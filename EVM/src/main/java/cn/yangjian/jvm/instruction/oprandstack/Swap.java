package cn.yangjian.jvm.instruction.oprandstack;

import cn.yangjian.jvm.instruction.Instruction;
import cn.yangjian.jvm.interpreter.Interpreter;
import cn.yangjian.jvm.runtime.JavaClass;
import cn.yangjian.jvm.runtime.RunTimeEnv;
import cn.yangjian.jvm.runtime.stack.JThread;
import cn.yangjian.jvm.utils.CodeUtils;
import cn.yangjian.jvm.interpreter.CallSite;

public class Swap implements Instruction {
    @Override
    public void execute(RunTimeEnv runTimeEnv, JThread jThread, JavaClass javaClass, CallSite callSite, Interpreter interpreter, CodeUtils code) {
        System.out.println("未实现 " + this.getClass().getName());
    }
}
