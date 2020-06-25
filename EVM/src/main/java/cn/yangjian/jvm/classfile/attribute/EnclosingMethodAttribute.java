package cn.yangjian.jvm.classfile.attribute;

import cn.yangjian.jvm.classfile.basestruct.unsignednumber.U2;
import cn.yangjian.jvm.classfile.basestruct.unsignednumber.U4;

public class EnclosingMethodAttribute extends AttributeBase {
    public U2 attributeNameIndex;
    public U4 attributeLength;
    public U2 classIndex;
    public U2 methodIndex;
}
