package cn.yangjian.jvm.classfile.attribute.RuntimeTypeAnnotations;


import cn.yangjian.jvm.classfile.attribute.AttributeBase;
import cn.yangjian.jvm.classfile.basestruct.unsignednumber.U2;
import cn.yangjian.jvm.classfile.basestruct.unsignednumber.U4;

/**
 * java8增加
 */
public class RuntimeVisibleTypeAnnotationsAttribute extends AttributeBase {
    public U2 attributeNameIndex;
    public U4 attributeLength;
    public U2 numAnnotations;
    TypeAnnotation[] annotations;
}
