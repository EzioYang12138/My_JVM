package cn.yangjian.jvm.classfile.attribute.LocalVariableTable;

import cn.yangjian.jvm.classfile.basestruct.unsignednumber.U2;

public class LocalVariable {
    public U2 startPc;
    public U2 length;
    public U2 nameIndex;
    public U2 descriptorIndex;
    public U2 index;
}
