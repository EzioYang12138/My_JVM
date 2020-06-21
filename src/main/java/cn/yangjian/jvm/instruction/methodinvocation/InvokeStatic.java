package cn.yangjian.jvm.instruction.methodinvocation;

import cn.yangjian.jvm.classfile.basestruct.info.MethodInfo;
import cn.yangjian.jvm.classfile.constantpool.literal.ConstantBase;
import cn.yangjian.jvm.instruction.Instruction;
import cn.yangjian.jvm.interpreter.*;
import cn.yangjian.jvm.jnative.NativeUtils;
import cn.yangjian.jvm.runtime.JavaClass;
import cn.yangjian.jvm.runtime.RunTimeEnv;
import cn.yangjian.jvm.runtime.methodarea.MethodArea;
import cn.yangjian.jvm.runtime.stack.JThread;
import cn.yangjian.jvm.runtime.stack.JavaFrame;
import cn.yangjian.jvm.runtime.stack.LocalVars;
import cn.yangjian.jvm.runtime.stack.OperandStack;
import cn.yangjian.jvm.utils.CodeUtils;

public class InvokeStatic implements Instruction {

    @Override
    public void execute(RunTimeEnv runTimeEnv, JThread jThread, JavaClass javaClass, CallSite callSite, Interpreter interpreter, CodeUtils code) {
        JavaFrame javaFrame = jThread.getTopFrame();
        OperandStack operandStack = javaFrame.operandStack;
        LocalVars localVars = javaFrame.localVars;

        short invokeIndex = code.consumeU2();
        ConstantBase[] constant_bases = javaClass.getClassFile().constantPool.cpInfo;
        ConstantBase constant_base = constant_bases[invokeIndex - 1];
        invokeStatic(runTimeEnv, jThread, interpreter,javaClass,constant_base);
    }

    private void invokeStatic(RunTimeEnv runTimeEnv, JThread jThread, Interpreter interpreter, JavaClass javaClass, ConstantBase constant_base) {

        Ref ref = JavaClass.processRef(javaClass, constant_base);
        JavaClass curClass = runTimeEnv.methodArea.findClass(ref.className);
        if(curClass == null){
            curClass = runTimeEnv.methodArea.loadClass(ref.className);
            runTimeEnv.methodArea.linkClass(ref.className);
            runTimeEnv.methodArea.initClass(ref.className, interpreter);
        }

        MethodInfo method_info = curClass.findMethod(ref.refName, ref.descriptorName);

        if (method_info == null){
            return ;
        }

        /*注册过的native方法才执行*/
        if(MethodArea.isNative(method_info.accessFlags) && NativeUtils.hasNativeMethod(ref)){
            return;
        }
        Descriptor descriptor = JavaClass.processDescriptor(ref.descriptorName);
        CallSite callSite = new CallSite();
        callSite.setCallSiteOrNative( method_info, descriptor.returnType);
        OperandStack invokerStack = jThread.getTopFrame().operandStack;
        jThread.pushFrame(callSite.maxStack, callSite.maxLocals);
        JavaFrame curFrame = jThread.getTopFrame();
        LocalVars curLocalVars = curFrame.localVars;

        /*调用传递参数*/
        int slotCount = JavaClass.calParametersSlot(method_info, descriptor.parameters);

        for(int i = 0; i < slotCount; i++){
            curLocalVars.putSlot(slotCount - 1 - i, invokerStack.popSlot());
        }

        interpreter.executeByteCode(jThread, curClass, callSite);
    }

}
