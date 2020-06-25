package cn.yangjian.jvm.instruction.methodinvocation;

import cn.yangjian.jvm.interpreter.Interpreter;
import cn.yangjian.jvm.runtime.JavaClass;
import cn.yangjian.jvm.runtime.RunTimeEnv;
import cn.yangjian.jvm.runtime.stack.JThread;
import cn.yangjian.jvm.runtime.stack.LocalVars;
import cn.yangjian.jvm.runtime.stack.OperandStack;
import cn.yangjian.jvm.utils.CodeUtils;
import cn.yangjian.jvm.classfile.basestruct.info.MethodInfo;
import cn.yangjian.jvm.interpreter.CallSite;
import cn.yangjian.jvm.interpreter.Descriptor;
import cn.yangjian.jvm.interpreter.Ref;
import cn.yangjian.jvm.runtime.methodarea.constantpool.literal.ConstantBase;
import cn.yangjian.jvm.instruction.Instruction;
import cn.yangjian.jvm.runtime.stack.JavaFrame;

public class InvokeSpecial implements Instruction {


    @Override
    public void execute(RunTimeEnv runTimeEnv, JThread jThread, JavaClass javaClass, CallSite callSite, Interpreter interpreter, CodeUtils code) {
        JavaFrame javaFrame = jThread.getTopFrame();
        OperandStack operandStack = javaFrame.operandStack;
        LocalVars localVars = javaFrame.localVars;

        short invokeIndex = code.consumeU2();
        ConstantBase[] constant_bases = javaClass.getClassFile().constantPool.cpInfo;
        ConstantBase constant_methodref = constant_bases[invokeIndex - 1];
        Ref methodRef = JavaClass.processRef(javaClass, constant_methodref);

        invokeSpecial(runTimeEnv, jThread, interpreter, methodRef);
    }

    public void invokeSpecial(RunTimeEnv runTimeEnv, JThread jThread, Interpreter interpreter, Ref methodRef) {

        MethodInfo method_info = JavaClass.parseMethodRef(runTimeEnv, methodRef);
        /*调用传递参数 如(J)J*/
        Descriptor descriptor = JavaClass.processDescriptor(methodRef.descriptorName);

        CallSite callSite = new CallSite();
        callSite.setCallSite(method_info);
        OperandStack invokerStack = jThread.getTopFrame().operandStack;
        jThread.pushFrame(callSite.maxStack, callSite.maxLocals);
        JavaFrame curFrame = jThread.getTopFrame();
        LocalVars curLocalVars = curFrame.localVars;

        /*调用传递参数*/
        int slotCount = JavaClass.calParametersSlot(method_info, descriptor.parameters);

        for (int i = 0; i < slotCount; i++) {
            curLocalVars.putSlot(slotCount - 1 - i, invokerStack.popSlot());
        }
        interpreter.executeByteCode(jThread, method_info.javaClass, callSite);
    }
}
