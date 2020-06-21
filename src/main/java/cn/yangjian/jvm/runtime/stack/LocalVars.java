package cn.yangjian.jvm.runtime.stack;


import cn.yangjian.jvm.runtime.Vars;
import cn.yangjian.jvm.runtime.struct.Slot;

/**
 * 局部变量表     就是方法里边的局部变量
 */
public class LocalVars extends Vars {
    public LocalVars(Integer maxLocals) {
        slots = new Slot[maxLocals];
        for(int i = 0; i < maxLocals; i++){
            slots[i] = new Slot();
        }
    }
}
