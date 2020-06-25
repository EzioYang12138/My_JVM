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
import cn.yangjian.jvm.runtime.struct.JObject;

public class InvokeVirtual implements Instruction {

    @Override
    public void execute(RunTimeEnv runTimeEnv, JThread jThread, JavaClass javaClass, CallSite callSite, Interpreter interpreter, CodeUtils code) {
        JavaFrame javaFrame = jThread.getTopFrame();
        OperandStack operandStack = javaFrame.operandStack;
        LocalVars localVars = javaFrame.localVars;

        short invokeIndex = code.consumeU2();
        ConstantBase[] constant_bases = javaClass.getClassFile().constantPool.cpInfo;
        ConstantBase constant_methodref = constant_bases[invokeIndex - 1];
        invokeVirtual(runTimeEnv, jThread, interpreter, javaClass,constant_methodref);
    }

    private void invokeVirtual(RunTimeEnv runTimeEnv, JThread jThread, Interpreter interpreter, JavaClass javaClass, ConstantBase constant_methodref) {
        Ref methodRef = JavaClass.processRef(javaClass, constant_methodref);

        MethodInfo method_info = JavaClass.parseMethodRef(runTimeEnv, methodRef);
        /*调用传递参数 如(J)J*/
        Descriptor descriptor = JavaClass.processDescriptor(methodRef.descriptorName);

        JObject jObject = jThread.getTopFrame().operandStack.getJObjectFromTop(method_info.argSlotCount - 1);

        if(jObject == null){
            /*hack*/
            if("println".equals(methodRef.refName)){
                InvokeUtils._println(runTimeEnv, jThread.getTopFrame().operandStack, methodRef.descriptorName);
                return;
            }
        }
        /*类似 Father objectcreatemanipulate = new Son();Son 存在方法时，需要调用son的方法，而不是father的*/
        if(!methodRef.className.equals(jObject.javaClass.classPath) ){
            MethodInfo concreteClassMethod = jObject.javaClass.findMethod(methodRef.refName, methodRef.descriptorName);
            if(concreteClassMethod != null){
                methodRef.className = jObject.javaClass.classPath;
                method_info = JavaClass.parseMethodRef(runTimeEnv, methodRef);
            }
        }

        CallSite callSite = new CallSite();
        callSite.setCallSite( method_info);
        OperandStack invokerStack = jThread.getTopFrame().operandStack;
        jThread.pushFrame(callSite.maxStack, callSite.maxLocals);
        JavaFrame curFrame = jThread.getTopFrame();
        LocalVars curLocalVars = curFrame.localVars;

        /*调用传递参数*/
        int slotCount = JavaClass.calParametersSlot(method_info, descriptor.parameters);

        for(int i = 0; i < slotCount; i++){
            curLocalVars.putSlot(slotCount - 1 - i, invokerStack.popSlot());
        }

        interpreter.executeByteCode(jThread, method_info.javaClass, callSite);
    }




}
