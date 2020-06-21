package cn.yangjian.jvm.runtime.struct;


import cn.yangjian.jvm.runtime.JavaClass;

/**
 * 实例对象
 */
public class JObject extends JType {
    /**
     * 实例对象所属的类的引用
     */
    public JavaClass javaClass;
    /**
     * 实例对象在heap的偏移量
     */
    public Integer offset;
}
