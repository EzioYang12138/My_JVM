package cn.yangjian.jvm.classfile.attribute.stackmaptable;


import cn.yangjian.jvm.classfile.basestruct.unsignednumber.U1;
import cn.yangjian.jvm.classfile.basestruct.unsignednumber.U2;

public class ChopFrame extends StackMapFrame {
    public U1 chop = new U1();
    public U1 frameType = chop; /* 248-250 */
    public U2 offsetDelta;
}
