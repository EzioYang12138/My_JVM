package cn.yangjian.jvm.classfile.attribute.BootstrapMethods;

import cn.yangjian.jvm.classfile.basestruct.unsignednumber.U2;

public class BootstrapMethod {
    public U2 bootstrapMethodRef;
    public U2 numBootstrapArguments;
    public U2[] bootstrapArguments;
}
