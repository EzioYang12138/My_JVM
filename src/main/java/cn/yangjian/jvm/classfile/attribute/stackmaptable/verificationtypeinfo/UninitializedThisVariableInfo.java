package cn.yangjian.jvm.classfile.attribute.stackmaptable.verificationtypeinfo;


import cn.yangjian.jvm.classfile.basestruct.unsignednumber.U1;

public class UninitializedThisVariableInfo extends VerificationTypeInfo {
    public U1 itemUninitializedthis = new U1();
    public U1 tag = itemUninitializedthis; /* 6 */
}
