package cn.yangjian.jvm.instruction.objectcreatemanipulate;

import cn.yangjian.jvm.instruction.Instruction;
import cn.yangjian.jvm.interpreter.Interpreter;
import cn.yangjian.jvm.runtime.JavaClass;
import cn.yangjian.jvm.runtime.RunTimeEnv;
import cn.yangjian.jvm.runtime.StaticVars;
import cn.yangjian.jvm.runtime.stack.JThread;
import cn.yangjian.jvm.runtime.stack.JavaFrame;
import cn.yangjian.jvm.runtime.stack.LocalVars;
import cn.yangjian.jvm.runtime.stack.OperandStack;
import cn.yangjian.jvm.utils.CodeUtils;
import cn.yangjian.jvm.classfile.ClassFile;
import cn.yangjian.jvm.classfile.basestruct.info.FieldInfo;
import cn.yangjian.jvm.runtime.methodarea.constantpool.literal.ConstantBase;
import cn.yangjian.jvm.interpreter.CallSite;
import cn.yangjian.jvm.interpreter.Ref;


public class GetStatic implements Instruction {

    @Override
    public void execute(RunTimeEnv runTimeEnv, JThread jThread, JavaClass javaClass, CallSite callSite, Interpreter interpreter, CodeUtils code) {
        JavaFrame javaFrame = jThread.getTopFrame();
        OperandStack operandStack = javaFrame.operandStack;
        LocalVars localVars = javaFrame.localVars;

        short staticIndex = code.consumeU2();
        ClassFile classFile = javaClass.getClassFile();
        ConstantBase[] constant_bases = classFile.constantPool.cpInfo;
        ConstantBase constant_base = constant_bases[staticIndex - 1];
        Ref fieldRef = JavaClass.processRef(javaClass, constant_base);

        FieldInfo field_info = JavaClass.parseFieldRef(runTimeEnv, fieldRef, interpreter);
        int slotId = field_info.slotId;
        JavaClass javaClass1 = runTimeEnv.methodArea.findClass(fieldRef.className);
        StaticVars staticVars = javaClass1.staticVars;
        char s = fieldRef.descriptorName.charAt(0);
        if (s == 'Z' || s == 'B' || s == 'C' || s == 'S' || s == 'I') {
            operandStack.putInt(staticVars.getIntByIndex(slotId));
        } else if (s == 'J') {
            operandStack.putLong(staticVars.getLongByIndex(slotId));
        } else if (s == 'F') {
            operandStack.putFloat(staticVars.getFloat(slotId));
        } else if (s == 'D') {
            operandStack.putDouble(staticVars.getDouble(slotId));
        } else if (s == 'L' || s == '[') {
            /*bug*/
            operandStack.putJObject(staticVars.getJObject(slotId));
        }
    }
}
