package cn.yangjian.jvm.instruction.loadandstore.constant;

import cn.yangjian.jvm.instruction.Instruction;
import cn.yangjian.jvm.interpreter.Interpreter;
import cn.yangjian.jvm.runtime.JavaClass;
import cn.yangjian.jvm.runtime.RunTimeEnv;
import cn.yangjian.jvm.runtime.stack.JThread;
import cn.yangjian.jvm.runtime.stack.LocalVars;
import cn.yangjian.jvm.runtime.stack.OperandStack;
import cn.yangjian.jvm.utils.CodeUtils;
import cn.yangjian.jvm.classfile.basestruct.unsignednumber.U4;
import cn.yangjian.jvm.runtime.methodarea.constantpool.literal.ConstantBase;
import cn.yangjian.jvm.runtime.methodarea.constantpool.literal.ConstantDouble;
import cn.yangjian.jvm.runtime.methodarea.constantpool.literal.ConstantLong;
import cn.yangjian.jvm.interpreter.CallSite;
import cn.yangjian.jvm.runtime.stack.JavaFrame;
import cn.yangjian.jvm.utils.TypeUtils;

/**
 *
 */
public class Ldc2_W implements Instruction {
    @Override
    public void execute(RunTimeEnv runTimeEnv, JThread jThread, JavaClass javaClass, CallSite callSite, Interpreter interpreter, CodeUtils code) {
        JavaFrame javaFrame = jThread.getTopFrame();
        OperandStack operandStack = javaFrame.operandStack;
        LocalVars localVars = javaFrame.localVars;

        /*从常量池取值到frame顶*/
        short cpIndex = code.consumeU2();
        ConstantBase constant_base = javaClass.getClassFile().constantPool.cpInfo[cpIndex - 1];
        if (constant_base instanceof ConstantLong) {
            U4 highBytes = ((ConstantLong) constant_base).highBytes;
            U4 lowBytes = ((ConstantLong) constant_base).lowBytes;
            operandStack.putLong(TypeUtils.byteArr2Int(highBytes.u4), TypeUtils.byteArr2Int(lowBytes.u4));
        } else if (constant_base instanceof ConstantDouble) {
            U4 highBytes = ((ConstantDouble) constant_base).highBytes;
            U4 lowBytes = ((ConstantDouble) constant_base).lowBytes;
            byte[] doubleByte = TypeUtils.appendByte(highBytes.u4, lowBytes.u4);
            double ldcValue = TypeUtils.byteArr2Double(doubleByte);
            operandStack.putDouble(ldcValue);
        }
    }
}
