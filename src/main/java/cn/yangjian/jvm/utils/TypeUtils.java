package cn.yangjian.jvm.utils;

import cn.yangjian.jvm.classfile.basestruct.unsignednumber.U1;

public class TypeUtils {
    /**
     * byte[]转int
     * byte的范围是-128至127，此处视为无符号，0-255
     * @param bytes
     * @return
     */
    static public Integer byteArr2Int(byte[] bytes) {
        int size = bytes.length;
        /*bytes[0]为高位*/
        int res = 0xff & bytes[0];
        for (int i = 1; i < size; i++) {
            res = (res << 8)  + (0xff & bytes[i]);
        }
        return res;
    }

    static public long byteArr2Long(byte[] bytes) {
        int size = bytes.length;
        long res = 0xff & bytes[0];
        for (int i = 1; i < size; i++) {
            //res = ((res << 8) + (2 << (8 * i) - 1)) & bytes[i];
            res = (res << 8)  + ( bytes[i] & 0xff);
        }
        return res;
    }

    /**
     * byte转int
     * byte的范围是-128至127，此处视为无符号，0-255
     * @param value
     * @return
     */
    static public Integer byte2Int(byte value) {
        return 0xff & value;
    }

    static public float byteArr2Float(byte[] bytes) {
        return Float.intBitsToFloat(byteArr2Int(bytes));
    }

    static public double byteArr2Double(byte[] bytes) {

        return Double.longBitsToDouble(byteArr2Long(bytes));
    }

    static public int float2Int(float value) {
        return Float.floatToIntBits(value);
    }
    static public float int2Float(int value) {
        return Float.intBitsToFloat(value);
    }

    /**
     * byte[]转String
     *
     * @param bytes
     * @return
     */
    static public String byte2String(byte[] bytes) {
        int size = bytes.length;
        char[] res = new char[size];
        for (int i = 0; i < size; i++) {
            res[i] = (char) bytes[i];
        }
        return String.valueOf(res);
    }

    /**
     * U1[] to String
     *
     * @param bytes
     * @return
     */
    static public String u12String(U1[] bytes) {
        int size = bytes.length;
        char[] res = new char[size];
        for (int i = 0; i < size; i++) {
            res[i] = (char) bytes[i].u1[0];
        }
        return String.valueOf(res);
    }

    static public boolean compare(String s, String d){
        if(s == null && d == null){
            return true;
        }
        if(s == null || d == null){
            return false;
        }
        Integer len0 = s.length();
        Integer len1 = d.length();
        if(!len0.equals(len1)){
            return false;
        }

        char[] arr0 = s.toCharArray();
        char[] arr1 = d.toCharArray();
        for(int i = 0; i < len0; i++){
            if(arr0[i] != arr1[i]){
                return false;
            }
        }
        return true;
    }

    public static byte[] appendByte(byte[] high, byte[] low) {
        int len = high.length + low.length;
        byte[] res = new byte[len];
        int i = 0;

        for(int highLen = high.length; i < highLen; i++){
            res[i] = high[i];
        }
        for(int j = 0, lowLen = high.length; j < lowLen; j++){
            res[i ++] = low[j];
        }
        return res;
    }
}
