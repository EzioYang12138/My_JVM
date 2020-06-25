package cn.yangjian.jvm.jnative;


import cn.yangjian.jvm.runtime.RunTimeEnv;
import cn.yangjian.jvm.runtime.stack.JavaFrame;

public interface NativeMethod {
    /**
     * 调用本地方法
     * @param javaFrame
     */
    void invoke(RunTimeEnv runTimeEnv, JavaFrame javaFrame);
}
