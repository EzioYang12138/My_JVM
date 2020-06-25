package cn.yangjian.jvm.classfile.attribute.code;


import cn.yangjian.jvm.classfile.attribute.AttributeBase;
import cn.yangjian.jvm.classfile.basestruct.unsignednumber.U1;
import cn.yangjian.jvm.classfile.basestruct.unsignednumber.U2;
import cn.yangjian.jvm.classfile.basestruct.unsignednumber.U4;

public class CodeAttribute extends AttributeBase {
    public U2 attributeNameIndex;
    public U4 attributeLength;
    public U2 maxStack;
    public U2 maxLocals;
    public U4 codeLength;
    public U1[] code;
    public U2 exceptionTableLength;
    public ExceptionTable[] exceptionTables;
    public U2 attributeCount;
    public AttributeBase[] attributes;
}
