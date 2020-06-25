package cn.yangjian.jvm.runtime.methodarea.constantpool.symbolicreferences;


import cn.yangjian.jvm.runtime.methodarea.constantpool.literal.ConstantBase;
import cn.yangjian.jvm.classfile.basestruct.unsignednumber.U1;
import cn.yangjian.jvm.classfile.basestruct.unsignednumber.U2;

public class ConstantMethodref extends ConstantBase {
    public U1 tag;
    public U2 classIndex;
    public U2 nameAndTypeIndex;
}
