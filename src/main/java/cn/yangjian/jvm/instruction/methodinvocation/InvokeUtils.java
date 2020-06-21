package cn.yangjian.jvm.instruction.methodinvocation;

import cn.yangjian.jvm.classfile.basestruct.info.FieldInfo;
import cn.yangjian.jvm.runtime.RunTimeEnv;
import cn.yangjian.jvm.runtime.heap.ArrayFields;
import cn.yangjian.jvm.runtime.heap.ObjectFields;
import cn.yangjian.jvm.runtime.stack.OperandStack;
import cn.yangjian.jvm.runtime.struct.JObject;

public class InvokeUtils {
    /**
     * 本地方法hack
     * @param operandStack
     * @param descriptor
     */
    public static void _println(RunTimeEnv runTimeEnv, OperandStack operandStack, String descriptor) {
        if("(Z)V".equals(descriptor)){
            System.out.println(operandStack.popInt() != 0);
        }else if("(C)V".equals(descriptor)){
            System.out.println((char)operandStack.popInt());
        }else if("(I)V".equals(descriptor)
                ||"(B)V".equals(descriptor)||"(S)V".equals(descriptor)){
            System.out.println(operandStack.popInt());
        }else if("(F)V".equals(descriptor)){
            System.out.println(operandStack.popFloat());
        }else if("(J)V".equals(descriptor)){
            System.out.println(operandStack.popLong());
        }else if("(D)V".equals(descriptor)){
            System.out.println(operandStack.popDouble());
        }else if("(Ljava/lang/String;)V".equals(descriptor)) {
            JObject jObject = operandStack.popJObject();
            ObjectFields objectFields = runTimeEnv.javaHeap.objectContainer.get(jObject.offset);
            FieldInfo field_info = jObject.javaClass.findField("value","[C");
            JObject charArrObject = objectFields.getJObject(field_info.slotId);
            ArrayFields arrayFields = runTimeEnv.javaHeap.arrayContainer.get(charArrObject.offset);
            char[] chars = arrayFields.trans2CharArr();
            System.out.println(chars);
        }else {
            System.out.println("println " + descriptor);
        }
        operandStack.popJObject();
    }
}
