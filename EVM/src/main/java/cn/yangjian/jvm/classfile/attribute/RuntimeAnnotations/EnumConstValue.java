package cn.yangjian.jvm.classfile.attribute.RuntimeAnnotations;


import cn.yangjian.jvm.classfile.basestruct.unsignednumber.U1;
import cn.yangjian.jvm.classfile.basestruct.unsignednumber.U2;

public class EnumConstValue extends ElementValue {
public U1 tag;
    public U2 typeNameIndex;
    public U2 constNameIndex;
}
