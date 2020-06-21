package cn.yangjian.jvm.instruction.loadandstore.load;

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

public class Lload_0 implements Instruction {

    @Override
    public void execute(RunTimeEnv runTimeEnv, JThread jThread, JavaClass javaClass, CallSite callSite, Interpreter interpreter, CodeUtils codeUtils) {
        JavaFrame javaFrame = jThread.getTopFrame();
        OperandStack operandStack = javaFrame.operandStack;
        LocalVars localVars = javaFrame.localVars;

        long loadValue = localVars.getLongByIndex(0);
        operandStack.putLong(loadValue);
    }
}