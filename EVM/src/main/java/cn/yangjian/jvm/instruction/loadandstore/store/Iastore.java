package cn.yangjian.jvm.instruction.loadandstore.store;

import cn.yangjian.jvm.instruction.Instruction;
import cn.yangjian.jvm.interpreter.Interpreter;
import cn.yangjian.jvm.runtime.JavaClass;
import cn.yangjian.jvm.runtime.RunTimeEnv;
import cn.yangjian.jvm.runtime.heap.ArrayFields;
import cn.yangjian.jvm.runtime.stack.JThread;
import cn.yangjian.jvm.runtime.stack.JavaFrame;
import cn.yangjian.jvm.runtime.stack.LocalVars;
import cn.yangjian.jvm.runtime.stack.OperandStack;
import cn.yangjian.jvm.runtime.struct.JObject;
import cn.yangjian.jvm.utils.CodeUtils;
import cn.yangjian.jvm.interpreter.CallSite;

public class Iastore implements Instruction {
    @Override
    public void execute(RunTimeEnv runTimeEnv, JThread jThread, JavaClass javaClass, CallSite callSite, Interpreter interpreter, CodeUtils code) {
        JavaFrame javaFrame = jThread.getTopFrame();
        OperandStack operandStack = javaFrame.operandStack;
        LocalVars localVars = javaFrame.localVars;

        int value = operandStack.popInt();
        int index = operandStack.popInt();
        JObject arrayObject = operandStack.popJObject();
        ArrayFields arrayFields = runTimeEnv.javaHeap.arrayContainer.get(arrayObject.offset);
        arrayFields.putInt(index, value);
    }
}
