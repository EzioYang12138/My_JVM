package cn.yangjian.jvm.classfile.attribute.stackmaptable.verificationtypeinfo;


import cn.yangjian.jvm.classfile.basestruct.unsignednumber.U1;

public class LongVariableInfo extends VerificationTypeInfo {
    public U1 itemLong = new U1();
    public U1 tag = itemLong; /* 4 */
}
