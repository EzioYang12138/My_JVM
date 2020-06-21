package cn.yangjian.jvm.classfile.attribute.RuntimeAnnotations;


import cn.yangjian.jvm.classfile.basestruct.unsignednumber.U1;
import cn.yangjian.jvm.classfile.basestruct.unsignednumber.U2;

public class ArrayValue extends ElementValue {
public U1 tag;
    public U2 numValues;
    public ElementValue[] values;
}
