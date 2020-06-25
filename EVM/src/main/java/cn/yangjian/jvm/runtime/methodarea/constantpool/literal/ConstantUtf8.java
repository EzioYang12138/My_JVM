package cn.yangjian.jvm.runtime.methodarea.constantpool.literal;


import cn.yangjian.jvm.classfile.basestruct.unsignednumber.U1;
import cn.yangjian.jvm.classfile.basestruct.unsignednumber.U2;

public class ConstantUtf8 extends ConstantBase {
    public U1 tag;
    public U2 length;
    public U1[] bytes;
}
