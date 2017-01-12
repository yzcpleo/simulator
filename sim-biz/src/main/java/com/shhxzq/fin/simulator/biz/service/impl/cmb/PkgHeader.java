package com.shhxzq.fin.simulator.biz.service.impl.cmb;


/**
 * <p>Title: 资金处理系统</p>
 *
 * <p>Description: 对包头进行拆解处理</p>
 *
 * <p>Copyright: Copyright (c) 2009</p>
 *

 */
public class PkgHeader
{
    private String pkgFlag; //包标识Char(4)
    private String pkgLen; //包体长度Char(4)
    private String transCode; //交易码Char(4)
    private String bnkDate; //银行交易日期Char(8)
    private String bnkTime; //银行交易时间Char(6)
    private String bnkFlowNo; //银行交易流水Char(30)
    private String corpDate; //商户交易日期Char(8)
    private String corpTime; //商户交易时间Char(6)
    private String corpFlowNo; //商户交易流水Char(30)
    private String reqCorpNo; //请求机构号Char(10)
    private String ackCode; //通讯校验码	Char(16)

    private Integer pkgHeaderLen; //包头长度

    public PkgHeader(String inputStr) throws Exception
    {
        //初始化变量
        Init();

        int lenthCount = 0;

        //返回码
        lenthCount += pkgFlag.length();
        if (inputStr.length() > lenthCount)
        {
            pkgFlag = inputStr.substring(0, pkgFlag.length());
        }

        //包体长度
        lenthCount += pkgLen.length();
        if (inputStr.length() > lenthCount)
        {
            pkgLen = inputStr.substring(lenthCount - pkgLen.length(), lenthCount);
        }

        try
        {
            Integer.parseInt(pkgLen);
        }
        catch (Exception e)
        {
            throw new Exception("从报文头读取包体长度失败,读取的值为:" + pkgLen);
        }

        //交易代码
        lenthCount += transCode.length();
        if (inputStr.length() > lenthCount)
        {
            transCode = inputStr.substring(lenthCount - transCode.length(), lenthCount);
        }

        //银行交易日期
        lenthCount += bnkDate.length();
        if (inputStr.length() > lenthCount)
        {
            bnkDate = inputStr.substring(lenthCount - bnkDate.length(), lenthCount);
        }

        //银行交易时间
        lenthCount += bnkTime.length();
        if (inputStr.length() > lenthCount)
        {
            bnkTime = inputStr.substring(lenthCount - bnkTime.length(), lenthCount);
        }

        //银行交易流水
        lenthCount += bnkFlowNo.length();
        if (inputStr.length() > lenthCount)
        {
            bnkFlowNo = inputStr.substring(lenthCount - bnkFlowNo.length(), lenthCount);
        }

        //商户交易日期
        lenthCount += corpDate.length();
        if (inputStr.length() > lenthCount)
        {
            corpDate = inputStr.substring(lenthCount - corpDate.length(), lenthCount);
        }

        //商户交易时间
        lenthCount += corpTime.length();
        if (inputStr.length() > lenthCount)
        {
            corpTime = inputStr.substring(lenthCount - corpTime.length(), lenthCount);
        }

        //商户交易流水
        lenthCount += corpFlowNo.length();
        if (inputStr.length() > lenthCount)
        {
            corpFlowNo = inputStr.substring(lenthCount - corpFlowNo.length(), lenthCount);
        }

        //请求机构号
        lenthCount += reqCorpNo.length();
        if (inputStr.length() > lenthCount)
        {
            reqCorpNo = inputStr.substring(lenthCount - reqCorpNo.length(), lenthCount);
        }

        //通讯校验码
        lenthCount += ackCode.length();
        if (inputStr.length() > lenthCount)
        {
            ackCode = inputStr.substring(lenthCount - ackCode.length(), lenthCount);
        }

        /*
                 transCode = headstr.substring(8, 12);
                 bnkDate = headstr.substring(12, 20);
                 bnkTime = headstr.substring(20, 26);
                 bnkFlowNo = headstr.substring(26, 56);
                 corpDate = headstr.substring(56, 64);
                 corpTime = headstr.substring(64, 70);
                 corpFlowNo = headstr.substring(70, 100);
                 reqCorpNo = headstr.substring(100, 110);
                 ackCode = headstr.substring(110, 126);*/
    }

    public PkgHeader()
    {
        Init();
    }

    public void Init()
    {
        pkgFlag = new String(new char[4]);
        pkgLen = new String(new char[4]);
        transCode = new String(new char[4]);
        bnkDate = new String(new char[8]);
        bnkTime = new String(new char[6]);
        bnkFlowNo = new String(new char[30]);
        corpDate = new String(new char[8]);
        corpTime = new String(new char[6]);
        corpFlowNo = new String(new char[30]);
        reqCorpNo = new String(new char[10]);
        ackCode = new String(new char[16]);
    }

    public String getPackage()
    {
        StringBuffer sb = new StringBuffer();
        sb.append(pkgFlag);
        sb.append(pkgLen);
        sb.append(transCode);
        sb.append(bnkDate);
        sb.append(bnkTime);
        sb.append(bnkFlowNo);
        sb.append(corpDate);
        sb.append(corpTime);
        sb.append(corpFlowNo);
        sb.append(reqCorpNo);
        sb.append(ackCode);
        return sb.toString();

    }

    public int getPkgLenAsInt() throws Exception
    {
        try
        {
            return Integer.parseInt(pkgLen);
        }
        catch (NumberFormatException ex)
        {
            throw new NumberFormatException("将报文体长度字符转换为整型失败");
        }
    }

    public void setCorpTime(String corpTime) throws Exception
    {
        corpTime = getFillStr(this.corpTime, corpTime);

        this.corpTime = corpTime;
    }

    public void setCorpFlowNo(String corpFlowNo) throws Exception
    {
        corpFlowNo = getFillStr(this.corpFlowNo, corpFlowNo);

        this.corpFlowNo = corpFlowNo;
    }

    public void setCorpDate(String corpDate) throws Exception
    {
        corpDate = getFillStr(this.corpDate, corpDate);

        this.corpDate = corpDate;
    }

    public void setBnkTime(String bnkTime) throws Exception
    {
        bnkTime = getFillStr(this.bnkTime, bnkTime);

        this.bnkTime = bnkTime;
    }

    public void setBnkFlowNo(String bnkFlowNo) throws Exception
    {
        bnkFlowNo = getFillStr(this.bnkFlowNo, bnkFlowNo);

        this.bnkFlowNo = bnkFlowNo;
    }

    public void setBnkDate(String bnkDate) throws Exception
    {
        bnkDate = getFillStr(this.bnkDate, bnkDate);

        this.bnkDate = bnkDate;
    }

    public void setAckCode(String ackCode) throws Exception
    {
        ackCode = getFillStr(this.ackCode, ackCode);

        this.ackCode = ackCode;
    }

    public void setTransCode(String transCode) throws Exception
    {
        transCode = getFillStr(this.transCode, transCode);

        this.transCode = transCode;
    }

    public void setReqCorpNo(String reqCorpNo) throws Exception
    {
        reqCorpNo = getFillStr(this.reqCorpNo, reqCorpNo);

        this.reqCorpNo = reqCorpNo;
    }

    public void setPkgLen(String pkgLen) throws Exception
    {
        pkgLen = getFillStrWithLeftZero(this.pkgLen, pkgLen);

        this.pkgLen = pkgLen;
    }

    public void setPkgFlag(String pkgFlag) throws Exception
    {
        pkgFlag = getFillStr(this.pkgFlag, pkgFlag);

        this.pkgFlag = pkgFlag;
    }

    public String getAckCode()
    {
        return ackCode;
    }

    public String getBnkDate()
    {
        return bnkDate;
    }

    public String getBnkFlowNo()
    {
        return bnkFlowNo;
    }

    public String getBnkTime()
    {
        return bnkTime;
    }

    public String getCorpDate()
    {
        return corpDate;
    }

    public String getCorpFlowNo()
    {
        return corpFlowNo;
    }

    public String getCorpTime()
    {
        return corpTime;
    }

    public String getPkgFlag()
    {
        return pkgFlag;
    }

    public String getPkgLen()
    {
        return pkgLen;
    }

    public String getReqCorpNo()
    {
        return reqCorpNo;
    }

    public String getTransCode()
    {
        return transCode;
    }

    /**
     * 获取包头长度
     * @return Integer
     */
    public Integer getPkgHeaderLen()
    {
        int iPkgHeaderLen = 0;

        if (pkgFlag != null)
        {
            iPkgHeaderLen += pkgFlag.length();
        }

        if (pkgLen != null)
        {
            iPkgHeaderLen += pkgLen.length();
        }

        if (transCode != null)
        {
            iPkgHeaderLen += transCode.length();
        }

        if (bnkDate != null)
        {
            iPkgHeaderLen += bnkDate.length();
        }

        if (bnkTime != null)
        {
            iPkgHeaderLen += bnkTime.length();
        }

        if (bnkFlowNo != null)
        {
            iPkgHeaderLen += bnkFlowNo.length();
        }

        if (corpDate != null)
        {
            iPkgHeaderLen += corpDate.length();
        }

        if (corpTime != null)
        {
            iPkgHeaderLen += corpTime.length();
        }

        if (corpFlowNo != null)
        {
            iPkgHeaderLen += corpFlowNo.length();
        }

        if (reqCorpNo != null)
        {
            iPkgHeaderLen += reqCorpNo.length();
        }

        if (ackCode != null)
        {
            iPkgHeaderLen += ackCode.length();
        }

        pkgHeaderLen = new Integer(iPkgHeaderLen);

        return pkgHeaderLen;
    }

    public String getFillStr(String oldStr, String newStr) throws Exception
    {
        String returnStr = null;
        try
        {
            returnStr = Common.getStrWithRightBlank(oldStr, newStr);
        }
        catch (Exception e)
        {
            //e.printStackTrace();
            throw new Exception(e.getMessage());
        }
        return returnStr;
    }


    public String getFillStrWithLeftZero(String oldStr, String newStr) throws Exception
    {
        String returnStr = null;
        try
        {
            returnStr = Common.getStrWithLeftZero(oldStr, newStr);
        }
        catch (Exception e)
        {
            //e.printStackTrace();
            throw new Exception(e.getMessage());
        }
        return returnStr;
    }


}
