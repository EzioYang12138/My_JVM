package cn.yangjian.jvm.classfile.attribute;


import cn.yangjian.jvm.classfile.attribute.RuntimeAnnotations.ElementValue;
import cn.yangjian.jvm.classfile.basestruct.unsignednumber.U2;
import cn.yangjian.jvm.classfile.basestruct.unsignednumber.U4;

public class AnnotationDefaultAttribute extends AttributeBase {
    public U2 attributeNameIndex;
    public U4 attributeLength;
    public ElementValue defaultValue;
}
