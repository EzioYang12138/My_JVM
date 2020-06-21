package cn.yangjian.jvm.instruction.arithmetic.relation;

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

public class Lcmp implements Instruction {
    @Override
    public void execute(RunTimeEnv runTimeEnv, JThread jThread, JavaClass javaClass, CallSite callSite, Interpreter interpreter, CodeUtils code) {
        JavaFrame javaFrame = jThread.getTopFrame();
        OperandStack operandStack = javaFrame.operandStack;
        LocalVars localVars = javaFrame.localVars;

        /*未考虑出现NaN的情况*/
        float var1 = operandStack.popFloat();
        float var0 = operandStack.popFloat();
        int cmpRes = 0;
        if (var0 > var1) {
            cmpRes = 1;
        } else if (var0 < var1) {
            cmpRes = -1;
        }
        operandStack.putInt(cmpRes);
    }
}
