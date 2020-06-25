package cn.yangjian.jvm.classfile.attribute.RuntimeAnnotations;


import cn.yangjian.jvm.classfile.basestruct.unsignednumber.U2;

public class Annotation {
    public U2 typeIndex;
    public U2 numElementValuePairs;
        public ElementValuePair[] elementValuePairs;
}
