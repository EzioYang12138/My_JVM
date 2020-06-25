package cn.yangjian.jvm.classfile.attribute.stackmaptable;

import cn.yangjian.jvm.classfile.basestruct.unsignednumber.U1;
import cn.yangjian.jvm.classfile.basestruct.unsignednumber.U2;
import cn.yangjian.jvm.classfile.attribute.stackmaptable.verificationtypeinfo.VerificationTypeInfo;

public class SameLocals1StackItemFrameExtended extends StackMapFrame {
    public U1 sameLocals1StackItemExtended = new U1();
    public U1 frameType = sameLocals1StackItemExtended;/* 247 */
    public U2 offsetDelta;
    public VerificationTypeInfo[] stack;
}
