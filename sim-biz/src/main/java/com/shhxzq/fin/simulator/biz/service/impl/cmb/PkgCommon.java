package com.shhxzq.fin.simulator.biz.service.impl.cmb;

/**
 * <p>Title: 资金处理系统</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2009</p>
 *

 */
public class PkgCommon
{

    /**
     * added by huluobo 与招行通信都是以字节传输
     * 银行传回的串要转换成byte[]数组进行分析
     * （注：分析字符串注意中文有2个字节，其他为1个字节，故转成byte数组方便分析）
     */
    protected String bankMessage; //输入字符串
    protected byte[] byteBankMessage; //输入字符串转化成字节数组
    protected int lenthCount; //计数器
    protected int byteBankMessageLen; //输入字符串转化成字节数组长度

    public PkgCommon()
    {

    }

    /**
     * 补齐字符串中的字符
     * @param oldStr String
     * @param newStr String
     * @return String
     */
    protected String getFillStr(String oldStr, String newStr) throws Exception
    {
        String returnStr = null;
        try
        {
            returnStr = Common.getStrWithRightBlank(oldStr, newStr);
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
        return returnStr;
    }

    /**
     * 补齐字符串中的字符
     * @param oldStr String
     * @param newStr String
     * @return String
     */
    protected String getFillZeroStr(String oldStr, String newStr) throws Exception
    {
        String returnStr = null;
        try
        {
            returnStr = Common.getStrWithLeftZero(oldStr, newStr);
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
        return returnStr;
    }

    /**
     * 获取各个字段的值（要按照招行的文档的字段顺序调用此方法）
     * @param inputFieldValue String
     * @return String
     */
    protected String getFieldValue(String inputFieldValue) throws Exception
    {
        if (inputFieldValue == null)
        {
            throw new Exception("inputFieldValue字段为空！");
        }

        String returnValue = null;
        byte[] byteInputFieldValue = inputFieldValue.getBytes();

        lenthCount += byteInputFieldValue.length;
        if (byteBankMessageLen >= lenthCount)
        {
            returnValue = new String(byteBankMessage, (lenthCount - byteInputFieldValue.length), byteInputFieldValue.length);
        }

        return returnValue;
    }
}
