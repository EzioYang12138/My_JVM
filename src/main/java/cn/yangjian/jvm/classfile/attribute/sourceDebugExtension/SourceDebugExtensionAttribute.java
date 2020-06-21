package cn.yangjian.jvm.classfile.attribute.sourceDebugExtension;

import cn.yangjian.jvm.classfile.attribute.AttributeBase;
import cn.yangjian.jvm.classfile.basestruct.unsignednumber.U1;
import cn.yangjian.jvm.classfile.basestruct.unsignednumber.U2;
import cn.yangjian.jvm.classfile.basestruct.unsignednumber.U4;

public class SourceDebugExtensionAttribute extends AttributeBase {
    public U2 attributeNameIndex;
    public U4 attributeLength;
    public U1[] debugExtension;
}
