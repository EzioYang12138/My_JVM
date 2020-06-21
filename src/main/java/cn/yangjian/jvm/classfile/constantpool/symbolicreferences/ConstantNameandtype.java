package cn.yangjian.jvm.classfile.constantpool.symbolicreferences;


import cn.yangjian.jvm.classfile.basestruct.unsignednumber.U1;
import cn.yangjian.jvm.classfile.basestruct.unsignednumber.U2;
import cn.yangjian.jvm.classfile.constantpool.literal.ConstantBase;

//字段或方法的部分符号引用
public class ConstantNameandtype extends ConstantBase {
    public U1 tag;
    public U2 nameIndex;
    public U2 descriptorIndex;
}
