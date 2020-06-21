package cn.yangjian.jvm.interpreter;

import cn.yangjian.jvm.classfile.attribute.code.CodeAttribute;
import cn.yangjian.jvm.classfile.attribute.code.ExceptionTable;
import cn.yangjian.jvm.classfile.basestruct.TypeCode;
import cn.yangjian.jvm.classfile.basestruct.info.MethodInfo;
import cn.yangjian.jvm.classfile.basestruct.unsignednumber.U1;
import cn.yangjian.jvm.classfile.basestruct.unsignednumber.U2;
import cn.yangjian.jvm.classfile.basestruct.unsignednumber.U4;
import cn.yangjian.jvm.runtime.methodarea.MethodArea;
import cn.yangjian.jvm.utils.TypeUtils;

/**
 * 作为方法调用的运行时的具体描述
 */
public class CallSite {
    public U2 accessFlags;
    public U2 nameIndex;
    public U2 descriptorIndex;
    public U2 maxStack;
    public U2 maxLocals;
    public U4 codeLength;
    public U1[] code;
    public U2 exceptionTableLength;
    public ExceptionTable[] exceptionTables;

    public CallSite(){
        maxStack = new U2();
        maxStack.u2 = new byte[1];
        maxLocals = new U2();
        maxLocals.u2 = new byte[1];
        codeLength = new U4();
    }

    /**
     * 可能是native方法，需要设置max_stack、maxLocals、注入字节码等
     * @param methodInfo
     * @param returnType
     */
    public void setCallSiteOrNative(MethodInfo methodInfo, Integer returnType){
        accessFlags = methodInfo.accessFlags;
        /*对于native方法，注入字节码*/
        if(MethodArea.isNative(accessFlags)){
            nameIndex = methodInfo.nameIndex;
            descriptorIndex = methodInfo.descriptorIndex;
            maxStack.u2 = new byte[]{0x4};
            maxLocals.u2 = new byte[]{0x5};
            codeLength.u4 = new byte[]{0x2};
            code = new U1[2];
            code[0] = new U1();
            code[0].u1 = new byte[1];
            code[1] = new U1();
            code[1].u1 = new byte[1];
            code[0].u1[0] = (byte) (0x0ff & 0xfe);

            if(TypeCode.T_EXTRA_VOID.equals(returnType)){
                code[1].u1[0] = (byte) (0x0ff & 0xb1);// return
            }else if (TypeCode.T_EXTRA_OBJECT.equals(returnType)
                    || TypeCode.T_EXTRA_ARRAY.equals(returnType)){
                code[1].u1[0] = (byte) (0x0ff & 0xb0); //ARETURN
            }else if(TypeCode.T_DOUBLE.equals(returnType)){
                code[1].u1[0] = (byte) (0x0ff & 0xaf); //dreturb
            }else if (TypeCode.T_FLOAT.equals(returnType)){
                code[1].u1[0] = (byte) (0x0ff & 0xae);//FRETURN
            }else if(TypeCode.T_LONG.equals(returnType)){
                code[1].u1[0] = (byte) (0x0ff & 0xad);//LRETURN
            }else  {
                code[1].u1[0] = (byte) (0x0ff & 0xac); //IRETURN
            }
            return;
        }
        setCallSite(methodInfo);
    }

    public void setCallSite(MethodInfo methodInfo) {
        accessFlags = methodInfo.accessFlags;
        nameIndex = methodInfo.nameIndex;
        descriptorIndex = methodInfo.descriptorIndex;
        Integer count = TypeUtils.byteArr2Int(methodInfo.attributeCount.u2);

        for(int i = 0; i < count; i++){
            if(methodInfo.attributes[i] instanceof CodeAttribute){
                CodeAttribute codeAttribute = (CodeAttribute) methodInfo.attributes[i];
                maxStack = codeAttribute.maxStack;
                maxLocals = codeAttribute.maxLocals;
                codeLength = codeAttribute.codeLength;
                code = codeAttribute.code;
                exceptionTableLength = codeAttribute.exceptionTableLength;
                exceptionTables = codeAttribute.exceptionTables;
                break;
            }
        }
    }
}
