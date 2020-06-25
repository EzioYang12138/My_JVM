package cn.yangjian.jvm.runtime.methodarea.constantpool.symbolicreferences;

import cn.yangjian.jvm.classfile.basestruct.unsignednumber.U1;
import cn.yangjian.jvm.classfile.basestruct.unsignednumber.U2;
import cn.yangjian.jvm.runtime.methodarea.constantpool.literal.ConstantBase;

public class ConstantMethodhandle extends ConstantBase {
    public U1 tag;
    public U1 referenceKind;
    public U2 referenceIndex;
}
