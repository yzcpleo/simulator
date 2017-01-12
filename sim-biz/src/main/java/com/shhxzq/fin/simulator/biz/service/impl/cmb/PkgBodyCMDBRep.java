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
 * <p>Remark: 对总帐 银行返回</p>
 *
 * @author 
 * @version 1.0
 */
public class PkgBodyCMDBRep extends PkgCommon
{
    private String retCode; //返回码
    private String orgBnkDate; //原银行账务日期
    private String orgBnkFlowNo; //原银行交易流水
    private String orgRetCode; //原交易状态
    private String transDetail; //交易信息描述

    private Integer pkgBodyLen; //包体长度

    public PkgBodyCMDBRep()
    {
        Init();
    }

    public PkgBodyCMDBRep(String inputStr) throws Exception
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
        orgBnkDate = getFieldValue(orgBnkDate);
        orgBnkFlowNo = getFieldValue(orgBnkFlowNo);
        orgRetCode = getFieldValue(orgRetCode);
        transDetail = getFieldValue(transDetail);

    }

    public void Init()
    {
        retCode = new String(new char[7]); //返回码
        orgBnkDate = new String(new char[8]);
        orgBnkFlowNo = new String(new char[30]);
        orgRetCode = new String(new char[7]);
        transDetail = new String(new char[80]);
    }

    public String getRetCode()
    {
        return retCode;
    }

    public String getOrgBnkDate()
    {
        return orgBnkDate;
    }

    public String getOrgBnkFlowNo()
    {
        return orgBnkFlowNo;
    }

    public String getOrgRetCode()
    {
        return orgRetCode;
    }

    public String getTransDetail()
    {
        return transDetail;
    }

    /**
     * 获取包体长度
     * @return Integer
     */
    public Integer getPkgBodyLen()
    {
        int iPkgBodyLen = 0;

        //返回码
        if (retCode != null)
        {
            iPkgBodyLen += retCode.getBytes().length;
        }

        if (orgBnkDate != null)
        {
            iPkgBodyLen += orgBnkDate.getBytes().length;
        }

        if (orgBnkFlowNo != null)
        {
            iPkgBodyLen += orgBnkFlowNo.getBytes().length;
        }

        if (orgRetCode != null)
        {
            iPkgBodyLen += orgRetCode.getBytes().length;
        }

        if (transDetail != null)
        {
            iPkgBodyLen += transDetail.getBytes().length;
        }

        pkgBodyLen = new Integer(iPkgBodyLen);
        return pkgBodyLen;
    }

    public void setOrgBnkDate(String orgBnkDate) throws Exception {
        orgBnkDate = getFillStr(this.orgBnkDate, orgBnkDate);
        this.orgBnkDate = orgBnkDate;
    }

    public void setOrgBnkFlowNo(String orgBnkFlowNo) throws Exception {
        orgBnkFlowNo = getFillStr(this.orgBnkFlowNo, orgBnkFlowNo);
        this.orgBnkFlowNo = orgBnkFlowNo;
    }

    public void setOrgRetCode(String orgRetCode) throws Exception {
        orgRetCode = getFillStr(this.orgRetCode, orgRetCode);
        this.orgRetCode = orgRetCode;
    }

    public void setRetCode(String retCode) throws Exception {
        retCode = getFillStr(this.retCode, retCode);
        this.retCode = retCode;
    }

    public void setTransDetail(String transDetail) throws Exception {
        transDetail = getFillStr(this.transDetail, transDetail);
        this.transDetail = transDetail;
    }

    public String getPackage()
    {
        StringBuffer sb = new StringBuffer();
        sb.append(retCode);
        sb.append(orgBnkDate);
        sb.append(orgBnkFlowNo);
        sb.append(orgRetCode);
        sb.append(transDetail);

        return sb.toString();
    }

}
