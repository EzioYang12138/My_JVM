package cn.yangjian.jvm.classfile.attribute.stackmaptable;

import cn.yangjian.jvm.classfile.attribute.stackmaptable.verificationtypeinfo.VerificationTypeInfo;
import cn.yangjian.jvm.classfile.basestruct.unsignednumber.U1;
import cn.yangjian.jvm.classfile.basestruct.unsignednumber.U2;

public class FullFrame extends StackMapFrame {
    public U1 fullFrame = new U1();
    public U1 frameType = fullFrame; /* 255 */
    public U2 offsetDelta;
    public U2 numberOfLocals;
    public VerificationTypeInfo[] locals;
    public U2 numberOfStackItems;
    public VerificationTypeInfo[] stack;
}
