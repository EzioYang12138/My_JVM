package cn.yangjian.jvm.runtime;

import cn.yangjian.jvm.EVM;
import cn.yangjian.jvm.classfile.ClassFile;
import cn.yangjian.jvm.classfile.basestruct.TypeCode;
import cn.yangjian.jvm.classfile.basestruct.info.FieldInfo;
import cn.yangjian.jvm.classfile.basestruct.info.MethodInfo;
import cn.yangjian.jvm.classfile.basestruct.unsignednumber.U2;
import cn.yangjian.jvm.classfile.constantpool.literal.ConstantBase;
import cn.yangjian.jvm.classfile.constantpool.literal.ConstantUtf8;
import cn.yangjian.jvm.classfile.constantpool.symbolicreferences.ConstantClass;
import cn.yangjian.jvm.classfile.constantpool.symbolicreferences.ConstantFieldref;
import cn.yangjian.jvm.classfile.constantpool.symbolicreferences.ConstantMethodref;
import cn.yangjian.jvm.classfile.constantpool.symbolicreferences.ConstantNameandtype;
import cn.yangjian.jvm.interpreter.Descriptor;
import cn.yangjian.jvm.interpreter.Interpreter;
import cn.yangjian.jvm.interpreter.Ref;
import cn.yangjian.jvm.runtime.methodarea.MethodArea;
import cn.yangjian.jvm.utils.TypeUtils;
import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

/**
 * classFile是读取字节码为有规范的数据结构。
 * 运行时表示：ClassFile的入口，加一些类的信息
 */
public class JavaClass {
    public static final Integer CLASS_BYTECODE_FILE_MAX = 63325;
    public int staticFieldSlotCount;
    public int instanceFieldSlotCount;
    public StaticVars staticVars;
    public String superClassName;

    public String classPath;
    private ClassFile classFile;
    public boolean isInited = false;


    public JavaClass(String classPath){
        this.classPath = classPath;
    }

    public MethodInfo findMethod(String methodName, String methodDescriptor){

        Integer methodSize = TypeUtils.byteArr2Int(classFile.methodsCount.u2);
        for(int i = 0; i < methodSize; i++){

            String cpMethodName = constantUtf8Index2String(classFile.methods[i].nameIndex);
            String cpMethodDescriptor = constantUtf8Index2String(classFile.methods[i].descriptorIndex);

            if(TypeUtils.compare(cpMethodName, methodName) && TypeUtils.compare(cpMethodDescriptor, methodDescriptor)){
                return classFile.methods[i];
            }
        }
        return null;
    }

    public FieldInfo findField(String fieldName, String fieldDescriptor){

        Integer fieldSize = TypeUtils.byteArr2Int(classFile.fieldCount.u2);
        for(int i = 0; i < fieldSize; i++){

            String cpFieldName = constantUtf8Index2String(classFile.fields[i].nameIndex);
            String cpFieldDescriptor = constantUtf8Index2String(classFile.fields[i].descriptorIndex);
            if(TypeUtils.compare(cpFieldName, fieldName) && TypeUtils.compare(cpFieldDescriptor, fieldDescriptor)){
                return classFile.fields[i];
            }
        }

        /*找不到在父类中找*/
        JavaClass superClass = EVM.evmEnv.methodArea.findClass(this.superClassName);
        if(superClass != null){
            return superClass.findField(fieldName, fieldDescriptor);
        }
        return null;
    }

    public void readBytecode2ClassFile(byte[] byteCode){


        this.classFile = new ClassFile();
        try {
            this.classFile.processByteCode(byteCode);
            //System.out.println(JSON.toJSONString(this.classFile));
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(JSON.toJSONString(this.classFile));
        }

    }

    /**
     * 传入ConstantUtf8 类型的index，在常量池中获得对应字符串
     * @param index
     * @return
     */
    public String constantUtf8Index2String(U2 index){
        ConstantUtf8 constantUtf8 = (ConstantUtf8) classFile.constantPool.cpInfo[TypeUtils.byteArr2Int(index.u2) - 1];
        return TypeUtils.u12String(constantUtf8.bytes);
    }

    public ClassFile getClassFile(){
        return this.classFile;
    }

    /**
     * 传入CONSTANT_Base(子类：ConstantFieldref、ConstantMethodref)，返回className，descriptorName，methodName(或fieldName)
     */
    public static Ref processRef(JavaClass javaClass, ConstantBase constant_base){
        Ref ref = new Ref();
        ConstantBase[] constant_bases = javaClass.getClassFile().constantPool.cpInfo;

        int class_index = 0;
        int name_and_type_index = 0;
        if(constant_base instanceof ConstantMethodref){
            ConstantMethodref methodref  = (ConstantMethodref) constant_base;
            class_index = TypeUtils.byteArr2Int(methodref.classIndex.u2);
            name_and_type_index = TypeUtils.byteArr2Int(methodref.nameAndTypeIndex.u2);
        }else if(constant_base instanceof ConstantFieldref){
            ConstantFieldref fieldref  = (ConstantFieldref) constant_base;
            class_index = TypeUtils.byteArr2Int(fieldref.classIndex.u2);
            name_and_type_index = TypeUtils.byteArr2Int(fieldref.nameAndTypeIndex.u2);
        }

        ConstantClass constant_class = (ConstantClass) constant_bases[class_index - 1];
        ConstantNameandtype constant_nameAndType = (ConstantNameandtype)constant_bases[name_and_type_index - 1];

        ConstantUtf8 methodNameUtf8 = (ConstantUtf8)constant_bases[TypeUtils.byteArr2Int(constant_nameAndType.nameIndex.u2) - 1];
        ConstantUtf8 descriptorNameUtf8 = (ConstantUtf8)constant_bases[TypeUtils.byteArr2Int(constant_nameAndType.descriptorIndex.u2) - 1];
        ConstantUtf8 classNameUtf8 = (ConstantUtf8)constant_bases[TypeUtils.byteArr2Int(constant_class.nameIndex.u2) - 1];

        ref.className = TypeUtils.u12String(classNameUtf8.bytes);
        ref.descriptorName = TypeUtils.u12String(descriptorNameUtf8.bytes);
        ref.refName = TypeUtils.u12String(methodNameUtf8.bytes);
        //System.out.println(JSON.toJSONString(ref));
        return ref;
    }

    /**
     * 解析字段
     * @param fieldRef
     */
    public static FieldInfo parseFieldRef(RunTimeEnv runTimeEnv, Ref fieldRef, Interpreter interpreter) {
        JavaClass javaClass = runTimeEnv.methodArea.loadClass(fieldRef.className);
        runTimeEnv.methodArea.linkClass(fieldRef.className);

        runTimeEnv.methodArea.initClass(fieldRef.className, interpreter);
        JavaClass javaClass1 = runTimeEnv.methodArea.findClass(fieldRef.className);
        FieldInfo field_info = javaClass1.findField(fieldRef.refName, fieldRef.descriptorName);
        return field_info;
    }

    /**
     * 解析方法:将方法的argSlotCount和javaClass赋值
     * @param methodRef
     */
    public static MethodInfo parseMethodRef(RunTimeEnv runTimeEnv, Ref methodRef) {
        runTimeEnv.methodArea.loadClass(methodRef.className);
        runTimeEnv.methodArea.linkClass(methodRef.className);
        //runTimeEnv.methodArea.initClass(methodRef.className, this);
        JavaClass javaClass = runTimeEnv.methodArea.findClass(methodRef.className);
        MethodInfo method_info = javaClass.findMethod(methodRef.refName, methodRef.descriptorName);
        method_info.javaClass = javaClass;
        /*argSlotCount未赋值过，则赋值*/
        if(method_info.argSlotCount == -1){
            Descriptor descriptor = processDescriptor(methodRef.descriptorName);
            int slotCount = calParametersSlot(method_info, descriptor.parameters);
            method_info.argSlotCount = slotCount;

        }
        return method_info;
    }

    /**
     * 解析方法的修饰符，如(J)J解析为如下形式：Descriptor：{parameters:[J],returnType:J}
     * @param descriptorName
     * @return
     */
    public static Descriptor processDescriptor(String descriptorName) {
        Descriptor descriptor = new Descriptor();
        List<Integer> parameters = new ArrayList<>();
        int returnType = 0;
        char[] descriptorNameArr = descriptorName.toCharArray();
        int i = 0;
        while (descriptorNameArr[i] != ')'){
            switch (descriptorNameArr[i]) {
                case 'B': {
                    parameters.add(TypeCode.T_BYTE);
                }
                break;
                case 'C': {
                    parameters.add(TypeCode.T_CHAR);
                }
                break;
                case 'D': {
                    parameters.add(TypeCode.T_DOUBLE);
                }
                break;
                case 'F': {
                    parameters.add(TypeCode.T_FLOAT);
                }
                break;
                case 'I': {
                    parameters.add(TypeCode.T_INT);
                }
                break;
                case 'J': {
                    parameters.add(TypeCode.T_LONG);
                }
                break;
                case 'S': {
                    parameters.add(TypeCode.T_SHORT);
                }
                break;
                case 'Z': {
                    parameters.add(TypeCode.T_BOOLEAN);
                }
                break;
                case '[': {
                    int arrayComponentType = ++i;
                    while (descriptorNameArr[arrayComponentType] == '[') {
                        arrayComponentType++;
                    }
                    i = arrayComponentType;
                    parameters.add(TypeCode.T_EXTRA_ARRAY);
                }
                break;
                case 'L': {
                    int objectType = i++;
                    while (descriptorNameArr[objectType] != ';') {
                        objectType++;
                    }
                    i = objectType;
                    parameters.add(TypeCode.T_EXTRA_OBJECT);
                }
                break;
            }
            i++;
        }
        int len = descriptorName.length();
        while (i < len) {
            switch (descriptorNameArr[i]) {
                case 'B':{
                    returnType = TypeCode.T_BYTE;
                }
                break;
                case 'C':{
                    returnType = TypeCode.T_CHAR;
                }
                break;
                case 'D':{
                    returnType = TypeCode.T_DOUBLE;
                }
                break;
                case 'F':{
                    returnType = TypeCode.T_FLOAT;
                }
                break;
                case 'I':{
                    returnType = TypeCode.T_INT;
                }
                break;
                case 'J':{
                    returnType = TypeCode.T_LONG;
                }
                break;
                case 'S':{
                    returnType = TypeCode.T_SHORT;
                }
                break;
                case 'Z':{
                    returnType = TypeCode.T_BOOLEAN;
                }
                break;
                case 'V':{
                    returnType = TypeCode.T_EXTRA_VOID;
                }
                break;
                case '[': {
                    returnType = TypeCode.T_EXTRA_ARRAY;
                }
                break;
                case 'L': {
                    returnType = TypeCode.T_EXTRA_OBJECT;
                }
                break;
            }
            i++;
        }
        descriptor.parameters = parameters;
        descriptor.returnType = returnType;
        return descriptor;
    }

    /**
     * 计算方法的参数占用Slot的个数
     * @param method_info
     * @param parameters
     * @return
     */
    public static int calParametersSlot(MethodInfo method_info, List<Integer> parameters){
        int parametersCount = parameters.size();

        int slotCount = parametersCount;
        for (Integer parameter : parameters) {
            if (parameter.equals(TypeCode.T_LONG)
                    || parameter.equals(TypeCode.T_DOUBLE)) {
                slotCount++;
            }
        }
        if(!MethodArea.isStatic(method_info.accessFlags)){
            slotCount ++;/*'this' 引用*/
        }
        return slotCount;
    }

    public static String classNameToArrayClassName(String className){
        className = "[L" + className + ";";
        return className;
    }
}
