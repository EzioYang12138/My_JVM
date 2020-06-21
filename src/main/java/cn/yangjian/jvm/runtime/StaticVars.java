package cn.yangjian.jvm.runtime;

import cn.yangjian.jvm.runtime.struct.Slot;

//StaticVars.java          #JavaClass中的静态字段分配内存

public class StaticVars extends Vars {
    public StaticVars(Integer staticSlotCount) {
        slots = new Slot[staticSlotCount];
        for(int i = 0; i < staticSlotCount; i++){
            slots[i] = new Slot();
        }
    }
}
