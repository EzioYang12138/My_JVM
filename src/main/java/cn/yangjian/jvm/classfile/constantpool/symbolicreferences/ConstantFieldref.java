package cn.yangjian.jvm.classfile.constantpool.symbolicreferences;


import cn.yangjian.jvm.classfile.basestruct.unsignednumber.U1;
import cn.yangjian.jvm.classfile.basestruct.unsignednumber.U2;
import cn.yangjian.jvm.classfile.constantpool.literal.ConstantBase;

public class ConstantFieldref extends ConstantBase {
    public U1 tag;
    public U2 classIndex;
    public U2 nameAndTypeIndex;
}
