package cn.yangjian.jvm.runtime.methodarea;

import cn.yangjian.jvm.interpreter.Interpreter;
import cn.yangjian.jvm.runtime.JavaClass;
import cn.yangjian.jvm.runtime.StaticVars;
import cn.yangjian.jvm.Terminal;
import cn.yangjian.jvm.classfile.ClassFile;
import cn.yangjian.jvm.classfile.EvmClassLoader;
import cn.yangjian.jvm.classfile.attribute.AttributeBase;
import cn.yangjian.jvm.classfile.attribute.constantValue.ConstantValueAttribute;
import cn.yangjian.jvm.classfile.basestruct.AccessFlag;
import cn.yangjian.jvm.classfile.basestruct.info.FieldInfo;
import cn.yangjian.jvm.classfile.basestruct.unsignednumber.U2;
import cn.yangjian.jvm.classfile.basestruct.unsignednumber.U4;
import cn.yangjian.jvm.runtime.methodarea.constantpool.literal.*;
import cn.yangjian.jvm.runtime.methodarea.constantpool.symbolicreferences.ConstantClass;
import cn.yangjian.jvm.interpreter.Ref;
import cn.yangjian.jvm.utils.TypeUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class MethodArea {
    Map<String, JavaClass> javaClasses = new HashMap<>();
    List<String> loadedClasses = new ArrayList<>();
    List<String> linkedClasses = new ArrayList<>();
    List<String> initedClasses = new ArrayList<>();

    EvmClassLoader zvmClassLoader = new EvmClassLoader();

    /**
     * hack，不支持调用<clinit>的类，如Arrays的<clinit>包含断言相关的方法调用*/
    List<String> unSupportInitClasses = Arrays.asList("java/util/Arrays","java/lang/Math");

    /**
     * 加载类 .class结尾的需要时绝对路径。否则需要是类似ch07/Vector2D的类名
     * @param classPath
     *
     * load 之后，将为新生对象分配内存(指针碰撞(可能线程不安全 , 1.采用CAS配上失败重试 2.每个线程在堆中预先分配一小块
     * 内存，称为本地线程分配缓冲TLAB)，空闲列表)
     */
    public JavaClass loadClass(String classPath){
        if(classPath.startsWith("[")){
            return loadArrayClass(classPath);
        }

        if(loadedClasses.contains(classPath)){
            return findClass(classPath);
        }

        JavaClass javaClass = new JavaClass(classPath);
        byte[] bytecode = readClass(classPath);
        javaClass.readBytecode2ClassFile(bytecode);
        ClassFile classFile = javaClass.getClassFile();
        if(!"java/lang/Object".equals(classPath)){
            String superClassName = getSuperClassName(classFile);
            if(!loadedClasses.contains(superClassName)){
                loadClass(superClassName);
            }
            javaClass.superClassName = superClassName;
        }

        javaClasses.put(classPath, javaClass);
        loadedClasses.add(classPath);
        return javaClass;
    }

    public JavaClass loadArrayClass(String classPath){
        JavaClass javaClass = new JavaClass(classPath);
        javaClass.superClassName = "java/lang/Object";
        javaClasses.put(classPath, javaClass);
        return javaClass;
    }

    public String getSuperClassName(ClassFile classFile){
        int cpIndex = TypeUtils.byteArr2Int(classFile.superClass.u2);
        ConstantClass constantClass = (ConstantClass) classFile.constantPool.cpInfo[cpIndex - 1];
        int classCpIndex = TypeUtils.byteArr2Int(constantClass.nameIndex.u2);
        ConstantUtf8 constantUtf8 = (ConstantUtf8) classFile.constantPool.cpInfo[classCpIndex - 1];
        String superClassName = TypeUtils.u12String(constantUtf8.bytes);
        return superClassName;
    }

    private byte[] readClass(String path)  {
        path = Terminal.processPath(path);

        File classFile = new File(path);

        byte[] byteCode= new byte[JavaClass.CLASS_BYTECODE_FILE_MAX];

        try {
            FileInputStream in = new FileInputStream(classFile);
            in.read(byteCode);
        } catch (FileNotFoundException e) {
            System.out.println("file not found " + path);
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            System.out.println("读取 "+ path + " 失败");
            e.printStackTrace();
            return null;
        }
        return byteCode;
    }

    /**
     * 类似ch07/Vector2D的类名
     * @param className
     * @return
     */
    public JavaClass findClass(String className){
        boolean hasClass =  javaClasses.containsKey(className);
        if(hasClass){
            return javaClasses.get(className);
        }
        return null;
    }
    /**
     * 链接类
     * @param
     */
    public void linkClass(String classPath){
        if(linkedClasses.contains(classPath)){
            return;
        }

        JavaClass javaClass = findClass(classPath);
        if(!"java/lang/Object".equals(classPath)){
            String superClassName = getSuperClassName(javaClass.getClassFile());
            if(!linkedClasses.contains(superClassName)){
                linkClass(superClassName);
            }
        }
        verification();
        prepare(javaClass);
        resolved();
        linkedClasses.add(classPath);
    }

    /**
     * 验证
     */
    public void verification(){

    }

    /**
     * 准备
     * @param javaClass
     */
    public void prepare(JavaClass javaClass){
        calcInstanceFieldSlotIds(javaClass);
        calcStaticFieldSlotIds(javaClass);
        allocAndInitStaticVars(javaClass);
    }

    private void calcInstanceFieldSlotIds(JavaClass javaClass) {
        ClassFile classFile = javaClass.getClassFile();
        FieldInfo[] fieldInfos = classFile.fields;
        ConstantBase[] cp =  classFile.constantPool.cpInfo;
        int len = fieldInfos.length;
        int slotId = 0;
        if(javaClass.superClassName != null && !javaClass.superClassName.isEmpty()){
            JavaClass superClass = findClass(javaClass.superClassName);
            slotId = superClass.instanceFieldSlotCount;
        }
        for (FieldInfo fieldInfo : fieldInfos) {
            U2 accessFlags = fieldInfo.accessFlags;
            if (!isStatic(accessFlags)) {
                fieldInfo.slotId = slotId;
                slotId = countSlotId(slotId, fieldInfo, cp);
            }
        }
        javaClass.instanceFieldSlotCount = slotId;
    }

    private void calcStaticFieldSlotIds(JavaClass javaClass) {
        ClassFile classFile = javaClass.getClassFile();
        FieldInfo[] fieldInfos = classFile.fields;
        ConstantBase[] cp =  classFile.constantPool.cpInfo;
        int len = fieldInfos.length;
        int slotId = 0;
        for (FieldInfo fieldInfo : fieldInfos) {
            U2 accessFlags = fieldInfo.accessFlags;
            if (isStatic(accessFlags)) {
                fieldInfo.slotId = slotId;
                slotId = countSlotId(slotId, fieldInfo, cp);
            }
        }
        javaClass.staticFieldSlotCount = slotId;
    }

    private void allocAndInitStaticVars(JavaClass javaClass) {
        ClassFile classFile = javaClass.getClassFile();
        FieldInfo[] fieldInfos = classFile.fields;
        ConstantBase[] cp =  classFile.constantPool.cpInfo;
        Integer staticSlotCount = javaClass.staticFieldSlotCount;
        javaClass.staticVars = new StaticVars(staticSlotCount);
        StaticVars staticVars = javaClass.staticVars;
        for (FieldInfo fieldInfo : fieldInfos) {
            U2 accessFlags = fieldInfo.accessFlags;
            int constValueIndex = getConstValueIndex(fieldInfo.attributes);

            if (isFinal(accessFlags) && isStatic(accessFlags)) {
                int slotId = fieldInfo.slotId;
                int descriptorIndex = TypeUtils.byteArr2Int(fieldInfo.descriptorIndex.u2);
                ConstantUtf8 constantUtf8 = (ConstantUtf8) cp[descriptorIndex - 1];
                String descriptorName = TypeUtils.u12String(constantUtf8.bytes);
                char s = descriptorName.charAt(0);
                if (s == 'Z' || s == 'B' || s == 'C' || s == 'S' || s == 'I') {
                    /*hack 如在java/util/Arrays类中含有assertionsDisabled字段，无值的*/
                    if (s == 'Z' && constValueIndex == 0) {
                        staticVars.putIntByIndex(slotId, 1);
                        continue;
                    }
                    ConstantInteger constantInteger = (ConstantInteger) cp[constValueIndex - 1];
                    int value = TypeUtils.byteArr2Int(constantInteger.bytes.u4);
                    staticVars.putIntByIndex(slotId, value);
                } else if (s == 'J') {
                    ConstantLong constantLong = (ConstantLong) cp[constValueIndex - 1];
                    U4 highBytes = constantLong.highBytes;
                    U4 lowBytes = constantLong.lowBytes;
                    staticVars.putLong(slotId, TypeUtils.byteArr2Int(highBytes.u4), TypeUtils.byteArr2Int(lowBytes.u4));
                } else if (s == 'F') {
                    ConstantFloat constantFloat = (ConstantFloat) cp[constValueIndex - 1];
                    U4 ldcBytes = constantFloat.bytes;
                    float value = TypeUtils.byteArr2Float(ldcBytes.u4);
                    staticVars.putFloat(slotId, value);
                } else if (s == 'D') {
                    ConstantDouble constantDouble = (ConstantDouble) cp[constValueIndex - 1];
                    U4 highBytes = constantDouble.highBytes;
                    U4 lowBytes = constantDouble.lowBytes;
                    staticVars.putLong(slotId, TypeUtils.byteArr2Int(highBytes.u4), TypeUtils.byteArr2Int(lowBytes.u4));
                } else if (s == 'L' || s == '[') {
                    /*to do*/

                }

            }
        }
    }

    /**
     * 解析
     */
    public void resolved(){

    }

    /**
     * 初始化类
     */
    public void initClass(String classPath, Interpreter interpreter){
        Ref ref = new Ref();
        ref.className = classPath;
        ref.descriptorName = "()V";
        ref.refName = "<clinit>";
        JavaClass javaClass = findClass(classPath);
        /*不存在clinit ，不需要init*/
        if(javaClass == null || javaClass.findMethod(ref.refName, ref.descriptorName) == null){
            return;
        }
        /*跳过一些不支持的初始化方法*/
        if(unSupportInitClasses.contains(classPath)){
            return;
        }
        //System.out.println(new Gson().toJson(ref));
        if(!javaClass.isInited){
            interpreter.invokeSpecial(ref);
        }
        javaClass.isInited = true;
    }


    private int getConstValueIndex(AttributeBase[] attributes) {
        int constValueIndex = 0;
        int len = attributes.length;
        for(int i = 0; i < len; i++){
            if(attributes[i] instanceof ConstantValueAttribute){
                ConstantValueAttribute constantValueAttribute = (ConstantValueAttribute) attributes[i];
                constValueIndex = TypeUtils.byteArr2Int(constantValueAttribute.constantvalueIndex.u2);
                return constValueIndex;
            }
        }
        return constValueIndex;
    }

    private boolean isLongOrDouble(String descriptorName) {
        return descriptorName.startsWith("J") || descriptorName.startsWith("D");
    }

    static public boolean isStatic(U2 accessFlags) {
        int flags = TypeUtils.byteArr2Int(accessFlags.u2);
        return 0 != (flags & AccessFlag.ACC_STATIC);
    }

    static public boolean isNative(U2 accessFlags) {
        int flags = TypeUtils.byteArr2Int(accessFlags.u2);
        return 0 != (flags & AccessFlag.ACC_NATIVE);
    }

    private boolean isFinal(U2 accessFlags) {
        int flags = TypeUtils.byteArr2Int(accessFlags.u2);
        return 0 != (flags & AccessFlag.ACC_FINAL);
    }

    private int countSlotId(int slotId, FieldInfo fieldInfo, ConstantBase[] cp) {
        slotId ++;
        int descriptorIndex = TypeUtils.byteArr2Int(fieldInfo.descriptorIndex.u2);
        ConstantUtf8 constantUtf8 = (ConstantUtf8) cp[descriptorIndex - 1];
        String descriptorName = TypeUtils.u12String(constantUtf8.bytes);
        if(isLongOrDouble(descriptorName)){
            slotId++;
        }
        return slotId;
    }

}
