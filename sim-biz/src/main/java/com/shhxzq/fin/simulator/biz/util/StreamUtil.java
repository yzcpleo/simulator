package com.shhxzq.fin.simulator.biz.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 流操作工具类
 */
public class StreamUtil {
    
    /**
     * Read bytes with any given input stream
     * 
     * @param is
     * @return byte[]
     */
    public static final byte[] readBytes(InputStream is) {
        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();

            int data;
            while ((data = is.read()) != -1) {
                buffer.write(data);
            }

            return buffer.toByteArray();
        } catch (IOException e) {
        	throw new RuntimeException(e);
        }finally {
            try {
                if(is != null){
                    is.close();
                }
            } catch (IOException e) {
            	throw new RuntimeException(e);
            }
        }
    }
    
    /**
     * Read bytes from the socket input stream
     * 
     * @param is
     * @return byte[]
     */
    public static final byte[] readBytes(InputStream is, int contentLen) {
        if(contentLen > 0){
            int readLen = 0;
            
            int readLengthThisTime = 0;
            
            byte[] message = new byte[contentLen];
            
            try {
                
                while (readLen != contentLen) {
    
                    readLengthThisTime = is.read(message, readLen, contentLen - readLen);
    
                    if (readLengthThisTime == -1) {//Should not happen.
                        break;
                    }
    
                    readLen += readLengthThisTime;
                }
                
                return message;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        
        return new byte[] {};
    }
}
