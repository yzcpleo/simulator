package com.shhxzq.fin.simulator.biz.service.impl.cmb;

/**
 * <p>Title: 资金处理系统</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2009</p>
 *
 * <p>Company: </p>
 *
 * @author 
 * @version 1.0
 */
public class PkgBodyCMDMRep extends PkgCommon{
    private String retCode; //返回代码
    private String pinKey; //加密密码的密钥，使用DEKEY加密
    private String macKey; //数字校验密钥（MACKEY）使用DEKEY加密
    private String transDetail; //详细的交易信息说明
 

    public PkgBodyCMDMRep()
    {
        Init();
    }

    public PkgBodyCMDMRep(String inputStr) throws Exception
    {
        if (inputStr == null)
        {
            throw new Exception("报文返回字符串为空！");
        }

        //初始化变量
        Init();

        /**
         * 赋值并转换成byte数组
         */
        bankMessage = inputStr;
        byteBankMessage = inputStr.getBytes(); //转换成byte数组

        /**
         * 初始化计数器和银行返回信息长度
         */
        lenthCount = 0;
        byteBankMessageLen = byteBankMessage.length;

        /**
         * 注意，以下一定要按照招行文档的顺序，否则数据分析肯定报错！！
         */
        retCode = getFieldValue(retCode); //返回码
        pinKey = getFieldValue(pinKey); //密码密钥
        macKey = getFieldValue(macKey); //数据密钥
        transDetail = getFieldValue(transDetail); //交易金额交易信息描述

    }

    public void Init()
    {
        retCode = new String(new char[7]);
        pinKey = new String(new char[32]);
        macKey = new String(new char[32]);
        transDetail = new String(new char[80]);
    }

    public void setRetCode(String retCode) throws Exception
    {
        retCode = getFillStr(this.retCode, retCode);

        this.retCode = retCode;
    }

    public void setPinKey(String pinKey) throws Exception
    {
    	pinKey = getFillStr(this.pinKey, pinKey);

        this.pinKey = pinKey;
    }
    
    public void setMacKey(String macKey) throws Exception
    {
    	macKey = getFillStr(this.macKey, macKey);

        this.macKey = macKey;
    }


    public void setTransDetail(String transDetail) throws Exception
    {
        transDetail = getFillStr(this.transDetail, transDetail);

        this.transDetail = transDetail;
    }

    public String getRetCode()
    {
        return retCode;
    }


    public String getTransDetail()
    {
        return transDetail;
    }

    /**
     * 获取包体
     * @return String
     */
    public String getPackage()
    {
        StringBuffer sb = new StringBuffer();
        sb.append(retCode);
        sb.append(pinKey);
        sb.append(macKey);
        sb.append(transDetail);
        return sb.toString();
    }

	public String getPinKey() {
		return pinKey;
	}


	public String getMacKey() {
		return macKey;
	}

}

