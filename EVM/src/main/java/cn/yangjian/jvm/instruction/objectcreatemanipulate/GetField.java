package cn.yangjian.jvm.instruction.objectcreatemanipulate;

import cn.yangjian.jvm.instruction.Instruction;
import cn.yangjian.jvm.interpreter.Interpreter;
import cn.yangjian.jvm.runtime.JavaClass;
import cn.yangjian.jvm.runtime.RunTimeEnv;
import cn.yangjian.jvm.runtime.heap.ObjectFields;
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


public class GetField implements Instruction {

    @Override
    public void execute(RunTimeEnv runTimeEnv, JThread jThread, JavaClass javaClass, CallSite callSite, Interpreter interpreter, CodeUtils code) {
        JavaFrame javaFrame = jThread.getTopFrame();
        OperandStack operandStack = javaFrame.operandStack;
        LocalVars localVars = javaFrame.localVars;

        short fieldCpIndex = code.consumeU2();
        ConstantBase[] constant_bases = javaClass.getClassFile().constantPool.cpInfo;
        ConstantBase constant_fieldref = constant_bases[fieldCpIndex - 1];
        getField(jThread, runTimeEnv, interpreter, javaClass, constant_fieldref);
    }

    private void getField(JThread jThread, RunTimeEnv runTimeEnv, Interpreter interpreter, JavaClass javaClass, ConstantBase constant_fieldref) {
        OperandStack operandStack = jThread.getTopFrame().operandStack;
        Ref fieldRef = JavaClass.processRef(javaClass, constant_fieldref);
        JavaClass classOfCurField = runTimeEnv.methodArea.findClass(fieldRef.className);
        if (classOfCurField == null) {
            runTimeEnv.methodArea.loadClass(fieldRef.className);
            runTimeEnv.methodArea.linkClass(fieldRef.className);
            runTimeEnv.methodArea.initClass(fieldRef.className, interpreter);
        }
        FieldInfo field_info = classOfCurField.findField(fieldRef.refName, fieldRef.descriptorName);
        char s = fieldRef.descriptorName.charAt(0);
        JObject jObject = operandStack.popJObject();
        ObjectFields objectFields = runTimeEnv.javaHeap.objectContainer.get(jObject.offset);
        if (s == 'Z' || s == 'B' || s == 'C' || s == 'S' || s == 'I') {
            operandStack.putInt(objectFields.getIntByIndex(field_info.slotId));
        } else if (s == 'J') {
            operandStack.putLong(objectFields.getLongByIndex(field_info.slotId));
        } else if (s == 'F') {
            operandStack.putFloat(objectFields.getFloat(field_info.slotId));
        } else if (s == 'D') {
            operandStack.putDouble(objectFields.getDouble(field_info.slotId));
        } else if (s == 'L' || s == '[') {
            /*bug*/
            operandStack.putJObject(objectFields.getJObject(field_info.slotId));
        }
    }

}
