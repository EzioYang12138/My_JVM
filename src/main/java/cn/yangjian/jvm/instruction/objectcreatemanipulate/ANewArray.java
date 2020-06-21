package cn.yangjian.jvm.instruction.objectcreatemanipulate;

import cn.yangjian.jvm.classfile.basestruct.TypeCode;
import cn.yangjian.jvm.classfile.constantpool.literal.ConstantBase;
import cn.yangjian.jvm.classfile.constantpool.literal.ConstantUtf8;
import cn.yangjian.jvm.classfile.constantpool.symbolicreferences.ConstantClass;
import cn.yangjian.jvm.instruction.Instruction;
import cn.yangjian.jvm.interpreter.CallSite;
import cn.yangjian.jvm.interpreter.CodeUtils;
import cn.yangjian.jvm.interpreter.Interpreter;
import cn.yangjian.jvm.runtime.JavaClass;
import cn.yangjian.jvm.runtime.RunTimeEnv;
import cn.yangjian.jvm.runtime.stack.JThread;
import cn.yangjian.jvm.runtime.stack.JavaFrame;
import cn.yangjian.jvm.runtime.stack.LocalVars;
import cn.yangjian.jvm.runtime.stack.OperandStack;
import cn.yangjian.jvm.runtime.struct.JObject;
import cn.yangjian.jvm.utils.TypeUtils;

public class ANewArray implements Instruction {
    @Override
    public void execute(RunTimeEnv runTimeEnv, JThread jThread, JavaClass javaClass, CallSite callSite, Interpreter interpreter, CodeUtils code) {
        JavaFrame javaFrame = jThread.getTopFrame();
        OperandStack operandStack = javaFrame.operandStack;
        LocalVars localVars = javaFrame.localVars;

        int classIndex = code.consumeU2();
        int count = operandStack.popInt();
        JObject jArray = anewarray(runTimeEnv, jThread, interpreter, classIndex, javaClass, count);
        operandStack.putJObject(jArray);
    }

    /**
     * 引用类型数组创建
     *
     * @param classIndex
     * @param javaClass
     * @param count
     * @return
     */
    private JObject anewarray(RunTimeEnv runTimeEnv, JThread jThread, Interpreter interpreter, int classIndex, JavaClass javaClass, int count) {
        ConstantBase[] constant_bases = javaClass.getClassFile().constantPool.cpInfo;
        ConstantClass constant_class = (ConstantClass) constant_bases[classIndex - 1];
        ConstantUtf8 classNameUtf8 = (ConstantUtf8) constant_bases[TypeUtils.byteArr2Int(constant_class.nameIndex.u2) - 1];
        String className = TypeUtils.u12String(classNameUtf8.bytes);
        JavaClass curClass = runTimeEnv.methodArea.findClass(className);
        if (curClass == null) {
            curClass = runTimeEnv.methodArea.loadClass(className);
            runTimeEnv.methodArea.linkClass(className);
        }

        className = JavaClass.classNameToArrayClassName(className);

        JObject jObject = runTimeEnv.javaHeap.createJArray(curClass, TypeCode.T_EXTRA_OBJECT, count, runTimeEnv, jThread);

        return jObject;
    }
}
