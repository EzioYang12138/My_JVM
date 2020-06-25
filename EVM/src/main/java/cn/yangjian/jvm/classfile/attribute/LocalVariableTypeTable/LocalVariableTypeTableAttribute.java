package cn.yangjian.jvm.classfile.attribute.LocalVariableTypeTable;


import cn.yangjian.jvm.classfile.attribute.AttributeBase;
import cn.yangjian.jvm.classfile.basestruct.unsignednumber.U2;
import cn.yangjian.jvm.classfile.basestruct.unsignednumber.U4;

public class LocalVariableTypeTableAttribute extends AttributeBase {
    public U2 attributeNameIndex;
    public U4 attributeLength;
    public U2 localVariableTypeTableLength;
    public LocalVariableType[] localVariableTypes;
}
