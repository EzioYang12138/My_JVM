package cn.yangjian.jvm.classfile.attribute.stackmaptable.verificationtypeinfo;


import cn.yangjian.jvm.classfile.basestruct.unsignednumber.U1;
import cn.yangjian.jvm.classfile.basestruct.unsignednumber.U2;

public class UninitializedVariableInfo extends VerificationTypeInfo {
    public U1 itemUninitialized = new U1();
    public U1 tag = itemUninitialized; /* 8 */
    public U2 offset;
}
