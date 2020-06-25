package cn.yangjian.jvm.runtime.methodarea.constantpool.literal;

import cn.yangjian.jvm.classfile.basestruct.unsignednumber.U4;
import cn.yangjian.jvm.classfile.basestruct.unsignednumber.U1;

public class ConstantDouble extends ConstantBase {
    public U1 tag;
    public U4 highBytes;
    public U4 lowBytes;
}

