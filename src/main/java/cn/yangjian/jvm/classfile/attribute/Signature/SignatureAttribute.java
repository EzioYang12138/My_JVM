package cn.yangjian.jvm.classfile.attribute.Signature;

import cn.yangjian.jvm.classfile.attribute.AttributeBase;
import cn.yangjian.jvm.classfile.basestruct.unsignednumber.U2;
import cn.yangjian.jvm.classfile.basestruct.unsignednumber.U4;

//保存反射信息
public class SignatureAttribute extends AttributeBase {
    public U2 attributeNameIndex;
    public U4 attributeLength;
    public U2 signatureIndex;
}
