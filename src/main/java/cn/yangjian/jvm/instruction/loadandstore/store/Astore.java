package cn.yangjian.jvm.instruction.loadandstore.store;

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

public class Astore implements Instruction {
    @Override
    public void execute(RunTimeEnv runTimeEnv, JThread jThread, JavaClass javaClass, CallSite callSite, Interpreter interpreter, CodeUtils code) {
        JavaFrame javaFrame = jThread.getTopFrame();
        OperandStack operandStack = javaFrame.operandStack;
        LocalVars localVars = javaFrame.localVars;

        int index = code.consumeU1();
        localVars.putJObject(index, operandStack.popJObject());
    }
}
