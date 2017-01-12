package com.shhxzq.fin.simulator.web.util;

/**
 * @author kangyonggan
 * @since 16/6/2
 */
public class Base64Util {

    /**
     * base64加密
     * @param msg
     * @return
     * @throws Exception
     */
    public static String base64Encode(String msg) throws Exception {
        return Base64.encode(msg.getBytes("GBK"));
    }

    /**
     * base64解密
     * @param msg
     * @return
     * @throws Exception
     */
    public static String base64Decode(String msg) throws Exception {
        return new String(Base64.decode(msg), "GBK");
    }

    public static String base64Decode(String msg, String encoding) throws Exception {
        return new String(Base64.decode(msg), encoding);
    }

}
