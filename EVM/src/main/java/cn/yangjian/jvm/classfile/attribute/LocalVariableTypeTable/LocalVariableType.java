package cn.yangjian.jvm.classfile.attribute.LocalVariableTypeTable;

import cn.yangjian.jvm.classfile.basestruct.unsignednumber.U2;

public class LocalVariableType {
    public U2 startPc;
    public U2 length;
    public U2 nameIndex;
    public U2 signatureIndex;
    public U2 index;
}
