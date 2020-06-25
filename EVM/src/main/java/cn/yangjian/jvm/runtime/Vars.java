package cn.yangjian.jvm.runtime;


import cn.yangjian.jvm.runtime.struct.JObject;
import cn.yangjian.jvm.runtime.struct.JType;
import cn.yangjian.jvm.runtime.struct.Slot;
import cn.yangjian.jvm.utils.TypeUtils;

//供LocalVars、LocalVars、ObjectFields继承使用
public class Vars {
    public Slot[] slots;


    public void putSlot(Integer index, Slot slot){
        slots[index] = slot;
    }

    public void putIntByIndex(int index, int value){
        slots[index].value = value ;
    }

    public int getIntByIndex(int index){
        return slots[index].value;
    }

    public long getLongByIndex(int index){
        int low = slots[index].value;
        int high = slots[index + 1].value;
        //return (((long)high) << 32) | ((long)low);
        return (((long)high) << 32) | ((long)low & 0x0ffffffffL);
    }

    public void putLong(int index, int high, int low){
        slots[index].value = low;
        slots[index + 1].value = high;
    }
    public void putLong(int index, long value){
        slots[index].value = (int) value;
        slots[index + 1].value = (int) (value >> 32);
    }

    public void putFloat(int index, float value) {
        slots[index].value = TypeUtils.float2Int(value);
    }

    public float getFloat(int index){
        return TypeUtils.int2Float(getIntByIndex(index));
    }


    public void putDouble(int index, double value) {
        putLong(index, Double.doubleToRawLongBits(value));
    }

    public double getDouble(int index){
        long value = getLongByIndex(index);
        return Double.longBitsToDouble(value);
    }

    public void putJObject(int index, JObject jObject){
        slots[index].jType = jObject;
    }

    public JObject getJObject(int index){
        return (JObject) slots[index].jType;
    }

    public void putJType(int index, JType jType){
        slots[index].jType = jType;
    }

    public JType getJType(int index){
        return (JObject) slots[index].jType;
    }

}
