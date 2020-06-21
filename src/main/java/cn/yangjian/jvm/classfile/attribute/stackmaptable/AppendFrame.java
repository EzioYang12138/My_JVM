package cn.yangjian.jvm.classfile.attribute.stackmaptable;


import cn.yangjian.jvm.classfile.attribute.stackmaptable.verificationtypeinfo.VerificationTypeInfo;
import cn.yangjian.jvm.classfile.basestruct.unsignednumber.U1;
import cn.yangjian.jvm.classfile.basestruct.unsignednumber.U2;

public class AppendFrame extends StackMapFrame {
    public U1 append = new U1();
    /**
     * 252-254
     */
    public U1 frameType = append;
    public U2 offsetDelta;
    public VerificationTypeInfo[] locals;
}
