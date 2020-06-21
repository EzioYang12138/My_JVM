package cn.yangjian.jvm.instruction.methodinvocation;

import cn.yangjian.jvm.instruction.Instruction;
import cn.yangjian.jvm.interpreter.CallSite;
import cn.yangjian.jvm.utils.CodeUtils;
import cn.yangjian.jvm.interpreter.Interpreter;
import cn.yangjian.jvm.interpreter.Ref;
import cn.yangjian.jvm.jnative.NativeUtils;
import cn.yangjian.jvm.runtime.JavaClass;
import cn.yangjian.jvm.runtime.RunTimeEnv;
import cn.yangjian.jvm.runtime.stack.JThread;
import cn.yangjian.jvm.runtime.stack.JavaFrame;
import cn.yangjian.jvm.runtime.stack.LocalVars;
import cn.yangjian.jvm.runtime.stack.OperandStack;

public class InvokeNative implements Instruction {

    @Override
    public void execute(RunTimeEnv runTimeEnv, JThread jThread, JavaClass javaClass, CallSite callSite, Interpreter interpreter, CodeUtils code) {
        JavaFrame javaFrame = jThread.getTopFrame();
        OperandStack operandStack = javaFrame.operandStack;
        LocalVars localVars = javaFrame.localVars;

        invokeNative(runTimeEnv, jThread, javaClass, callSite);
    }

    /**
     * 调用native方法,暂时只支持arraycopy
     * @param javaClass
     * @param callSite
     */
    private void invokeNative(RunTimeEnv runTimeEnv, JThread jThread, JavaClass javaClass, CallSite callSite) {
        Ref methodRef = new Ref();
        methodRef.refName = javaClass.constantUtf8Index2String(callSite.nameIndex);
        methodRef.descriptorName = javaClass.constantUtf8Index2String(callSite.descriptorIndex);
        methodRef.className = javaClass.classPath;
        if(NativeUtils.hasNativeMethod(methodRef)){
            return;
        }
        JavaFrame javaFrame = jThread.getTopFrame();
        NativeUtils.executeMethod(runTimeEnv, javaFrame, methodRef);
    }
}
