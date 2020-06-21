package cn.yangjian.jvm.classfile.attribute.stackmaptable.verificationtypeinfo;


import cn.yangjian.jvm.classfile.basestruct.unsignednumber.U1;
import cn.yangjian.jvm.classfile.basestruct.unsignednumber.U2;

public class ObjectVariableInfo extends VerificationTypeInfo {
    public U1 itemObject = new U1();
    public U1 tag = itemObject; /* 7 */
    public U2 cpoolIndex;
}
