package cn.yangjian.jvm.runtime.heap;

import cn.yangjian.jvm.runtime.Vars;
import cn.yangjian.jvm.runtime.struct.Slot;

/**
 * 保存对象的所有字段
 */
public class ObjectFields extends Vars {
    public ObjectFields(Integer num) {
        slots = new Slot[num];
        for(int i = 0; i < num; i++){
            slots[i] = new Slot();
        }
    }
}
