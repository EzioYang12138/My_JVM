package cn.yangjian.jvm.classfile.constantpool.literal;

import cn.yangjian.jvm.classfile.basestruct.unsignednumber.U1;
import cn.yangjian.jvm.classfile.basestruct.unsignednumber.U2;

//表示一个动态方法调用点
public class ConstantInvokedynamic extends ConstantBase {
    public U1 tag;
    public U2 bootstrapMethodAttrIndex;
    public U2 nameAndTypeIndex;
}
