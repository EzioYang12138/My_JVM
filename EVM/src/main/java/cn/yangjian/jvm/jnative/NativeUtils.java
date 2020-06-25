package cn.yangjian.jvm.jnative;


import cn.yangjian.jvm.runtime.RunTimeEnv;
import cn.yangjian.jvm.interpreter.Ref;
import cn.yangjian.jvm.runtime.stack.JavaFrame;


public class NativeUtils {

    public NativeUtils() {

    }

    /**
     * 注册本地方法
     */
    public void registerNatives() {
        /**
         *  native.Register(jlSystem, "arraycopy", "(Ljava/lang/Object;ILjava/lang/Object;II)V", arraycopy)
         */

        String method = NativeConstant.ARRAYCOPY_CLASSNAME + "~" + NativeConstant.ARRAYCOPY_DESCRIPTOR + "~" + NativeConstant.ARRAYCOPY_METHODNAME;

        registerNative(method, System::arraycopy);
    }


    public void registerNative(String method, NativeMethod nativeMethod) {
        RunTimeEnv.nativeMethodMap.put(method, nativeMethod);
    }

    public static boolean hasNativeMethod(Ref ref) {
        String method = ref.className + "~" + ref.descriptorName + "~" + ref.refName;
        return !RunTimeEnv.nativeMethodMap.containsKey(method);
    }

    private static NativeMethod findNativeMethod(Ref ref) {
        String method = ref.className + "~" + ref.descriptorName + "~" + ref.refName;
        return RunTimeEnv.nativeMethodMap.get(method);
    }

    public static void executeMethod(RunTimeEnv runTimeEnv, JavaFrame javaFrame, Ref ref) {
        NativeMethod nativeMethod = findNativeMethod(ref);
        if (nativeMethod != null) {
            nativeMethod.invoke(runTimeEnv, javaFrame);
        }
    }
}
