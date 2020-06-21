package cn.yangjian.jvm.runtime;


import cn.yangjian.jvm.gc.GC;
import cn.yangjian.jvm.jnative.NativeMethod;
import cn.yangjian.jvm.runtime.heap.JavaHeap;
import cn.yangjian.jvm.runtime.methodarea.MethodArea;

import java.util.HashMap;
import java.util.Map;

/**
 * evm运行环境
 */
public class RunTimeEnv {

    public JavaHeap javaHeap;
    public MethodArea methodArea;
    public static Map<String, NativeMethod> nativeMethodMap;

    public GC gc;

    public RunTimeEnv(){
        methodArea = new MethodArea();
        javaHeap = new JavaHeap();
        nativeMethodMap = new HashMap<>();
        gc = new GC();
    }

}
