package cn.yangjian.jvm.classfile.attribute.RuntimeParameterAnnotations;


import cn.yangjian.jvm.classfile.attribute.AttributeBase;
import cn.yangjian.jvm.classfile.basestruct.unsignednumber.U1;
import cn.yangjian.jvm.classfile.basestruct.unsignednumber.U2;
import cn.yangjian.jvm.classfile.basestruct.unsignednumber.U4;

public class RuntimeInvisibleParameterAnnotationsAttribute extends AttributeBase {
    public U2 attributeNameIndex;
    public U4 attributeLength;
    public U1 numParameters;
    public ParameterAnnotation[] parameterAnnotations;
}
