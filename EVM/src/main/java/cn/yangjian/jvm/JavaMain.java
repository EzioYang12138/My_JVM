package cn.yangjian.jvm;

import cn.yangjian.jvm.jnative.NativeUtils;
import cn.yangjian.jvm.runtime.RunTimeEnv;

public class JavaMain {

    public static void main(String[] args) {
        Terminal.processTerminal(args);
        RunTimeEnv evmEnv = new RunTimeEnv();
        String curClassName = Terminal.curClassName;
        System.out.println("file path : " + curClassName);

        EVM evm = new EVM(evmEnv);

        NativeUtils nativeUtils = new NativeUtils();
        nativeUtils.registerNatives();


        evm.callMain("main", "([Ljava/lang/String;)V", curClassName);
    }
}
