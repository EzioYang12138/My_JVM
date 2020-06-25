package cn.yangjian.jvm.runtime.stack;


import cn.yangjian.jvm.runtime.Vars;
import cn.yangjian.jvm.runtime.struct.Slot;

/**
 * 局部变量表     就是方法里边的局部变量
 * 存放的就是基本数据类型，对象引用类型等，就是struct文件夹里边的
 */
public class LocalVars extends Vars {
    public LocalVars(Integer maxLocals) {
        slots = new Slot[maxLocals];
        for(int i = 0; i < maxLocals; i++){
            slots[i] = new Slot();
        }
    }
}
