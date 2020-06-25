package cn.yangjian.jvm.gc;

import cn.yangjian.jvm.runtime.RunTimeEnv;
import cn.yangjian.jvm.runtime.heap.ArrayFields;
import cn.yangjian.jvm.runtime.heap.JavaHeap;
import cn.yangjian.jvm.runtime.heap.ObjectFields;
import cn.yangjian.jvm.runtime.stack.*;
import cn.yangjian.jvm.runtime.struct.JObject;
import cn.yangjian.jvm.runtime.struct.JType;
import cn.yangjian.jvm.runtime.struct.Slot;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class GC {

    public static List<Integer> usedObjectList;
    public static List<Integer> usedArrayList;

    public GC(){
        usedObjectList = new ArrayList<Integer>();
        usedArrayList = new ArrayList<Integer>();
    }

    /**
     *
     * @param runTimeEnv
     */
    public static void gc(RunTimeEnv runTimeEnv, JThread jThread) {
        markAndSweep(runTimeEnv, jThread);
    }

    private static void markAndSweep(RunTimeEnv runTimeEnv, JThread jThread) {
        ThreadStack threadStack = jThread.getStack();
        JavaFrame curFrame = threadStack.topFrame;
        JavaHeap javaHeap = runTimeEnv.javaHeap;
        while (curFrame != null){
            OperandStack operandStack = curFrame.operandStack;
            int size = operandStack.size;
            for(int i = 0; i < size; i ++){
                Slot slot = operandStack.getSlot();
                JType jType = slot.jType;
                mark(jType, javaHeap);
            }

            LocalVars localVars = curFrame.localVars;
            Slot[] localVarsSlots = localVars.slots;
            for(Slot localVar:localVarsSlots){
                mark(localVar.jType, javaHeap);
            }

            curFrame = curFrame.lowerFrame;
        }

        /*静态成员变量的回收，待完成*/

        /*清除*/
        sweep(javaHeap);
    }



    /**
     * jType表示对象或数组
     * @param jType
     * @param javaHeap
     */
    private static void mark(JType jType, JavaHeap javaHeap) {
        if(jType instanceof JObject){
            /*数组也是JObject*/
            JObject jObject = (JObject) jType;
            int offset = jObject.offset;

            /*非数组对象*/
            if(jObject.javaClass != null && !jObject.javaClass.classPath.startsWith("[")){
                usedObjectList.add(offset);
                Map<Integer, ObjectFields> objectContainer = javaHeap.objectContainer;
                ObjectFields objectFields = objectContainer.get(offset);
                Slot[] fields = objectFields.slots;
                for(Slot field:fields){
                    JType jType1 = field.jType;
                    mark(jType1, javaHeap);
                }
                /*数组*/
            }else{
                usedArrayList.add(offset);
                Map<Integer, ArrayFields> arrayContainer = javaHeap.arrayContainer;
                ArrayFields arrayFields = arrayContainer.get(offset);
                JType[] fields = arrayFields.primitiveTypes;
                for(JType field:fields){
                    JType jType1 = field;
                    mark(jType1, javaHeap);
                }
            }
        }

    }

    /**
     * 清除
     */
    private static void sweep(JavaHeap javaHeap) {
        Map<Integer, ObjectFields> objectContainer = javaHeap.objectContainer;
        Map<Integer, ArrayFields> arrayContainer = javaHeap.arrayContainer;

        Iterator<Map.Entry<Integer, ObjectFields>> objectIterator = objectContainer.entrySet().iterator();
        while (objectIterator.hasNext()){
            Map.Entry<Integer,ObjectFields> entry = objectIterator.next();
            if(!usedObjectList.contains(entry.getKey())){
                objectIterator.remove();
            }
        }

        Iterator<Map.Entry<Integer, ArrayFields>> arrayIterator = arrayContainer.entrySet().iterator();
        while (arrayIterator.hasNext()){
            Map.Entry<Integer,ArrayFields> entry = arrayIterator.next();
            if(!usedArrayList.contains(entry.getKey())){
                arrayIterator.remove();
            }
        }
    }
}
