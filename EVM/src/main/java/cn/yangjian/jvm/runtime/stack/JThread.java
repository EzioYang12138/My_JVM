package cn.yangjian.jvm.runtime.stack;


import cn.yangjian.jvm.classfile.basestruct.unsignednumber.U2;

public class JThread {

    Integer pc;
    ThreadStack threadStack;


    public void pushFrame(U2 maxStack, U2 maxLocals){
        if(threadStack == null){
            threadStack = new ThreadStack();
        }
        threadStack.put(new JavaFrame(maxStack, maxLocals));
    }

    public void popFrame(){
        threadStack.pop();
    }

    public JavaFrame getTopFrame(){
        return threadStack.topFrame;
    }

    public ThreadStack getStack(){
        return threadStack;
    }
}
