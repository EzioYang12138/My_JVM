package cn.yangjian.jvm.instruction.objectcreatemanipulate;

import cn.yangjian.jvm.instruction.Instruction;
import cn.yangjian.jvm.interpreter.CallSite;
import cn.yangjian.jvm.utils.CodeUtils;
import cn.yangjian.jvm.interpreter.Interpreter;
import cn.yangjian.jvm.runtime.JavaClass;
import cn.yangjian.jvm.runtime.RunTimeEnv;
import cn.yangjian.jvm.runtime.stack.JThread;
import cn.yangjian.jvm.runtime.stack.JavaFrame;
import cn.yangjian.jvm.runtime.stack.LocalVars;
import cn.yangjian.jvm.runtime.stack.OperandStack;
import cn.yangjian.jvm.runtime.struct.JObject;

public class NewArray implements Instruction {
    @Override
    public void execute(RunTimeEnv runTimeEnv, JThread jThread, JavaClass javaClass, CallSite callSite, Interpreter interpreter, CodeUtils code) {
        JavaFrame javaFrame = jThread.getTopFrame();
        OperandStack operandStack = javaFrame.operandStack;
        LocalVars localVars = javaFrame.localVars;

        int arrayType = code.consumeU1();
        int count = operandStack.popInt();
        JObject jArray = newarray(runTimeEnv, jThread, arrayType,count);
        operandStack.putJObject(jArray);
    }

    private JObject newarray( RunTimeEnv runTimeEnv, JThread jThread, int arrayType, int count) {
        JavaClass arrayClass = PrimitiveArrayUtils.getPrimitiveArrayClass(runTimeEnv, arrayType, count);

        return runTimeEnv.javaHeap.createJArray(arrayClass,arrayType, count, runTimeEnv, jThread);
    }
}
