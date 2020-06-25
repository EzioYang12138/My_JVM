package cn.yangjian.jvm.instruction.objectcreatemanipulate;

import cn.yangjian.jvm.instruction.Instruction;
import cn.yangjian.jvm.interpreter.Interpreter;
import cn.yangjian.jvm.runtime.JavaClass;
import cn.yangjian.jvm.runtime.RunTimeEnv;
import cn.yangjian.jvm.runtime.stack.JThread;
import cn.yangjian.jvm.runtime.stack.JavaFrame;
import cn.yangjian.jvm.runtime.stack.LocalVars;
import cn.yangjian.jvm.runtime.stack.OperandStack;
import cn.yangjian.jvm.runtime.struct.JObject;
import cn.yangjian.jvm.utils.CodeUtils;
import cn.yangjian.jvm.runtime.methodarea.constantpool.literal.ConstantBase;
import cn.yangjian.jvm.runtime.methodarea.constantpool.literal.ConstantUtf8;
import cn.yangjian.jvm.runtime.methodarea.constantpool.symbolicreferences.ConstantClass;
import cn.yangjian.jvm.interpreter.CallSite;
import cn.yangjian.jvm.utils.TypeUtils;

public class New implements Instruction {

    @Override
    public void execute(RunTimeEnv runTimeEnv, JThread jThread, JavaClass javaClass, CallSite callSite, Interpreter interpreter, CodeUtils code) {
        JavaFrame javaFrame = jThread.getTopFrame();
        OperandStack operandStack = javaFrame.operandStack;
        LocalVars localVars = javaFrame.localVars;

        int newIndex = code.consumeU2();
        ConstantBase[] constant_bases = javaClass.getClassFile().constantPool.cpInfo;
        ConstantBase constant_base = constant_bases[newIndex - 1];
        int name_index = TypeUtils.byteArr2Int(((ConstantClass) constant_base).nameIndex.u2);
        ConstantUtf8 constant_utf8 = (ConstantUtf8) constant_bases[name_index - 1];
        JObject jObject = execNew(runTimeEnv, jThread, interpreter, javaClass, constant_utf8);
        operandStack.putJObject(jObject);
    }

    private JObject execNew(RunTimeEnv runTimeEnv, JThread jThread, Interpreter interpreter, JavaClass javaClass, ConstantUtf8 constant_utf8) {
        String className = TypeUtils.u12String(constant_utf8.bytes);
        if (runTimeEnv.methodArea.findClass(className) == null) {
            runTimeEnv.methodArea.loadClass(className);
            runTimeEnv.methodArea.linkClass(className);
            runTimeEnv.methodArea.initClass(className, interpreter);
        }
        return runTimeEnv.javaHeap.createJObject(runTimeEnv.methodArea.findClass(className), runTimeEnv, jThread);
    }
}
