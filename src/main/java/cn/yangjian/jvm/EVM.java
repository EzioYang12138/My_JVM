package cn.yangjian.jvm;

import cn.yangjian.jvm.interpreter.Interpreter;
import cn.yangjian.jvm.runtime.JavaClass;
import cn.yangjian.jvm.runtime.RunTimeEnv;

public class EVM {

    public static RunTimeEnv evmEnv;
    public EVM(RunTimeEnv evmEnv){
        EVM.evmEnv = evmEnv;
    }

    public void callMain(String main, String descriptor, String classPath){

        JavaClass javaClass = evmEnv.methodArea.loadClass(classPath);
        Interpreter interpreter = new Interpreter(evmEnv);
        interpreter.initInstructions();
        evmEnv.methodArea.linkClass(classPath);
        interpreter.invokeByName(javaClass, main, descriptor);
    }

}
