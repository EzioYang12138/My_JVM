package cn.yangjian.jvm.instruction.objectcreatemanipulate;

import cn.yangjian.jvm.classfile.basestruct.info.FieldInfo;
import cn.yangjian.jvm.classfile.constantpool.literal.ConstantBase;
import cn.yangjian.jvm.instruction.Instruction;
import cn.yangjian.jvm.interpreter.CallSite;
import cn.yangjian.jvm.interpreter.CodeUtils;
import cn.yangjian.jvm.interpreter.Interpreter;
import cn.yangjian.jvm.interpreter.Ref;
import cn.yangjian.jvm.runtime.JavaClass;
import cn.yangjian.jvm.runtime.RunTimeEnv;
import cn.yangjian.jvm.runtime.heap.ObjectFields;
import cn.yangjian.jvm.runtime.stack.JThread;
import cn.yangjian.jvm.runtime.stack.JavaFrame;
import cn.yangjian.jvm.runtime.stack.LocalVars;
import cn.yangjian.jvm.runtime.stack.OperandStack;
import cn.yangjian.jvm.runtime.struct.JObject;

public class PutField implements Instruction {

    @Override
    public void execute(RunTimeEnv runTimeEnv, JThread jThread, JavaClass javaClass, CallSite callSite, Interpreter interpreter, CodeUtils code) {
        JavaFrame javaFrame = jThread.getTopFrame();
        OperandStack operandStack = javaFrame.operandStack;
        LocalVars localVars = javaFrame.localVars;

        short fieldCpIndex = code.consumeU2();
        ConstantBase[] constant_bases = javaClass.getClassFile().constantPool.cpInfo;
        ConstantBase constant_fieldref = constant_bases[fieldCpIndex - 1];
        putField(jThread, runTimeEnv, javaClass, constant_fieldref);
    }

    /**
     * putfield指令
     *
     * @param javaClass
     * @param constant_fieldref
     */
    private void putField(JThread jThread, RunTimeEnv runTimeEnv, JavaClass javaClass, ConstantBase constant_fieldref) {
        OperandStack operandStack = jThread.getTopFrame().operandStack;
        Ref fieldRef = JavaClass.processRef(javaClass, constant_fieldref);
        FieldInfo field_info = javaClass.findField(fieldRef.refName, fieldRef.descriptorName);
        char s = fieldRef.descriptorName.charAt(0);

        if (s == 'Z' || s == 'B' || s == 'C' || s == 'S' || s == 'I') {
            int val = operandStack.popInt();
            JObject jObject = operandStack.popJObject();
            ObjectFields objectFields = runTimeEnv.javaHeap.objectContainer.get(jObject.offset);
            objectFields.putIntByIndex(field_info.slotId, val);
        } else if (s == 'J') {
            long val = operandStack.popLong();
            JObject jObject = operandStack.popJObject();
            ObjectFields objectFields = runTimeEnv.javaHeap.objectContainer.get(jObject.offset);
            objectFields.putLong(field_info.slotId, val);
        } else if (s == 'F') {
            float val = operandStack.popFloat();
            JObject jObject = operandStack.popJObject();
            ObjectFields objectFields = runTimeEnv.javaHeap.objectContainer.get(jObject.offset);
            objectFields.putFloat(field_info.slotId, val);
        } else if (s == 'D') {
            double val = operandStack.popDouble();
            JObject jObject = operandStack.popJObject();
            ObjectFields objectFields = runTimeEnv.javaHeap.objectContainer.get(jObject.offset);
            objectFields.putDouble(field_info.slotId, val);
        } else if (s == 'L') {
            JObject val = operandStack.popJObject();
            JObject jObject = operandStack.popJObject();
            ObjectFields objectFields = runTimeEnv.javaHeap.objectContainer.get(jObject.offset);
            objectFields.putJObject(field_info.slotId, val);
        } else if (s == '[') {
            /*这个是数组对象引用*/
            JObject val = operandStack.popJObject();
            /*将数组对象引用放入这个对象中*/
            JObject jObject = operandStack.popJObject();
            FieldInfo concreteFieldInfo = jObject.javaClass.findField(fieldRef.refName, fieldRef.descriptorName);
            ObjectFields objectFields = runTimeEnv.javaHeap.objectContainer.get(jObject.offset);
            objectFields.putJObject(concreteFieldInfo.slotId, val);
        }
    }

}
