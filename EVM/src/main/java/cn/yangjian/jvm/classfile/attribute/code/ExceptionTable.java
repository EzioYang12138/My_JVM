package cn.yangjian.jvm.classfile.attribute.code;


import cn.yangjian.jvm.classfile.basestruct.unsignednumber.U2;

public class ExceptionTable {
    public U2 startPc;
    public U2 endPc;
    public U2 handlerPc;
    public U2 catchType;
}
