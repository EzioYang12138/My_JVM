package cn.yangjian.jvm.classfile.constantpool.symbolicreferences;


import cn.yangjian.jvm.classfile.basestruct.unsignednumber.U1;
import cn.yangjian.jvm.classfile.basestruct.unsignednumber.U2;
import cn.yangjian.jvm.classfile.constantpool.literal.ConstantBase;

//类或接口的符号引用
public class ConstantClass extends ConstantBase {

//    tag是标志位，用于区分常量类型
    public U1 tag;

//    nameIndex 是常量池的索引值，它指向常量池中一个constant_utf8_info类型常量
//    此常量代表了这个类或接口的全限定名
    public U2 nameIndex;
}
