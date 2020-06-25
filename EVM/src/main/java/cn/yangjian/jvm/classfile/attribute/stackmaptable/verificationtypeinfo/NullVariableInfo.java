package cn.yangjian.jvm.classfile.attribute.stackmaptable.verificationtypeinfo;


import cn.yangjian.jvm.classfile.basestruct.unsignednumber.U1;

public class NullVariableInfo extends VerificationTypeInfo {
    public U1 itemNull = new U1();
    public U1 tag = itemNull; /* 5 */
}
