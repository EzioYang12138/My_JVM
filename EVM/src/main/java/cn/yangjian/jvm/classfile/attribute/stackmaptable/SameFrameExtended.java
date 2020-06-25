package cn.yangjian.jvm.classfile.attribute.stackmaptable;


import cn.yangjian.jvm.classfile.basestruct.unsignednumber.U1;
import cn.yangjian.jvm.classfile.basestruct.unsignednumber.U2;

public class SameFrameExtended extends StackMapFrame {
    public U1 sameFrameExtended = new U1();
    public U1 frameType = sameFrameExtended; /* 251 */
    public U2 offsetDelta;
}
