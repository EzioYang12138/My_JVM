package cn.yangjian.jvm.classfile.attribute.Synthetic;

import cn.yangjian.jvm.classfile.attribute.AttributeBase;
import cn.yangjian.jvm.classfile.basestruct.unsignednumber.U2;
import cn.yangjian.jvm.classfile.basestruct.unsignednumber.U4;

public class SyntheticAttribute extends AttributeBase {
    public U2 attributeNameIndex;
    public U4 attributeLength;
}
