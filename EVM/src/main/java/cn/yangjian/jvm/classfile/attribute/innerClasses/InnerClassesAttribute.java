package cn.yangjian.jvm.classfile.attribute.innerClasses;


import cn.yangjian.jvm.classfile.attribute.AttributeBase;
import cn.yangjian.jvm.classfile.basestruct.unsignednumber.U2;
import cn.yangjian.jvm.classfile.basestruct.unsignednumber.U4;

public class InnerClassesAttribute extends AttributeBase {
public U2 attributeNameIndex;
public U4 attributeLength;
public U2 numberOfClasses;
    public Classes[] classes;
}
