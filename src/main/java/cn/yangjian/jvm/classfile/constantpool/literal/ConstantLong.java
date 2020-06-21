package cn.yangjian.jvm.classfile.constantpool.literal;


import cn.yangjian.jvm.classfile.basestruct.unsignednumber.U1;
import cn.yangjian.jvm.classfile.basestruct.unsignednumber.U4;

public class ConstantLong extends ConstantBase {
    public U1 tag;
    public U4 highBytes;
    public U4 lowBytes;
}
