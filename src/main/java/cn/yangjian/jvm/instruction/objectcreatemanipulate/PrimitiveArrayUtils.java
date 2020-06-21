package cn.yangjian.jvm.instruction.objectcreatemanipulate;

import cn.yangjian.jvm.classfile.basestruct.TypeCode;

import cn.yangjian.jvm.runtime.JavaClass;
import cn.yangjian.jvm.runtime.RunTimeEnv;


public class PrimitiveArrayUtils {
    public static JavaClass getPrimitiveArrayClass(RunTimeEnv runTimeEnv, int arrayType, int count){
        String className = null;
        if(arrayType == TypeCode.T_BOOLEAN){
            className = "[Z";
        }else if(arrayType == TypeCode.T_CHAR){
            className = "[C";
        }else if(arrayType == TypeCode.T_FLOAT){
            className = "[F";

        }else if(arrayType == TypeCode.T_DOUBLE){
            className = "[D";

        }else if(arrayType == TypeCode.T_BYTE){
            className = "[B";

        }else if(arrayType == TypeCode.T_SHORT){
            className = "[S";

        }else if(arrayType == TypeCode.T_INT){
            className = "[I";

        }else if(arrayType == TypeCode.T_LONG){
            className = "[J";

        }
        JavaClass arrayClass = runTimeEnv.methodArea.loadClass(className);

        return arrayClass;
    }
}
