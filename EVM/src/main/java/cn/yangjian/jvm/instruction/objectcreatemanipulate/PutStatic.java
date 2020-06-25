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
import cn.yangjian.jvm.runtime.struct.JObject;
import cn.yangjian.jvm.utils.CodeUtils;
import cn.yangjian.jvm.classfile.basestruct.info.FieldInfo;
import cn.yangjian.jvm.runtime.methodarea.constantpool.literal.ConstantBase;
import cn.yangjian.jvm.interpreter.CallSite;
import cn.yangjian.jvm.interpreter.Ref;

public class PutStatic implements Instruction {

    @Override
    public void execute(RunTimeEnv runTimeEnv, JThread jThread, JavaClass javaClass, CallSite callSite, Interpreter interpreter, CodeUtils code) {
        JavaFrame javaFrame = jThread.getTopFrame();
        OperandStack operandStack = javaFrame.operandStack;
        LocalVars localVars = javaFrame.localVars;

        short fieldCpIndex = code.consumeU2();
        ConstantBase[] constant_bases = javaClass.getClassFile().constantPool.cpInfo;
        ConstantBase constant_fieldref = constant_bases[fieldCpIndex - 1];
        putStaticField(jThread, javaClass, constant_fieldref);
    }

    private void putStaticField(JThread jThread, JavaClass javaClass, ConstantBase constant_fieldref) {
        OperandStack operandStack = jThread.getTopFrame().operandStack;
        Ref fieldRef = JavaClass.processRef(javaClass, constant_fieldref);
        FieldInfo field_info = javaClass.findField(fieldRef.refName, fieldRef.descriptorName);
        field_info.javaClass = javaClass;
        char s = fieldRef.descriptorName.charAt(0);
        StaticVars staticVars = javaClass.staticVars;

        if (s == 'Z' || s == 'B' || s == 'C' || s == 'S' || s == 'I') {
            int val = operandStack.popInt();
            staticVars.putIntByIndex(field_info.slotId, val);
        } else if (s == 'J') {
            long val = operandStack.popLong();
            staticVars.putLong(field_info.slotId, val);
        } else if (s == 'F') {
            float val = operandStack.popFloat();
            staticVars.putFloat(field_info.slotId, val);
        } else if (s == 'D') {
            double val = operandStack.popDouble();
            staticVars.putDouble(field_info.slotId, val);
        } else if (s == 'L') {
            JObject val = operandStack.popJObject();
            staticVars.putJObject(field_info.slotId, val);
        } else if (s == '[') {
            JObject val = operandStack.popJObject();
            staticVars.putJObject(field_info.slotId, val);
        }
    }
}
