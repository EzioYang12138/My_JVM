package cn.yangjian.jvm.classfile.attribute.stackmaptable;


import cn.yangjian.jvm.classfile.basestruct.unsignednumber.U1;

public class SameFrame extends StackMapFrame {
    public U1 same = new U1();
    public U1 frameType = same;/* 0-63 */
}
