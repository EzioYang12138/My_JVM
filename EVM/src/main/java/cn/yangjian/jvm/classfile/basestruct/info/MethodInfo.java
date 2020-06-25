package cn.yangjian.jvm.classfile.basestruct.info;


import cn.yangjian.jvm.classfile.attribute.AttributeBase;
import cn.yangjian.jvm.classfile.basestruct.unsignednumber.U2;
import cn.yangjian.jvm.runtime.JavaClass;

public class MethodInfo {
    public U2 accessFlags;
    public U2 nameIndex;
    public U2 descriptorIndex;
    public U2 attributeCount;
    public AttributeBase[] attributes;

    public int argSlotCount = -1;
    public JavaClass javaClass;
}
