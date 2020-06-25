package cn.yangjian.jvm.runtime.methodarea.constantpool.literal;

import cn.yangjian.jvm.classfile.basestruct.unsignednumber.U1;
import cn.yangjian.jvm.classfile.basestruct.unsignednumber.U4;

public class ConstantFloat extends ConstantBase {
    public U1 tag;
    public U4 bytes;
}
