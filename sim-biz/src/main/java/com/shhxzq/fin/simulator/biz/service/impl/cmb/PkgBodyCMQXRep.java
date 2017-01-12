package com.shhxzq.fin.simulator.biz.service.impl.cmb;

/**
 * <p>Title: 资金处理系统</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2009</p>
 *

 */
public class PkgBodyCMQXRep extends PkgCommon
{
    private String retCode; //返回码
    private String bankDate;
    private String protocolNo;
    private String account;
    private String accountArea;
    private String curCode;
    private String amount;
    private String transDetail; //交易信息描述


    public PkgBodyCMQXRep()
    {
        Init();
    }

    public PkgBodyCMQXRep(String inputStr) throws Exception
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
        retCode = getFieldValue(retCode);//返回码
        bankDate =  getFieldValue(bankDate);
        protocolNo = getFieldValue(protocolNo);
        account = getFieldValue(account);
        accountArea = getFieldValue(accountArea);
        curCode = getFieldValue(curCode);
        amount = getFieldValue(amount);
        transDetail = getFieldValue(transDetail);//交易金额交易信息描述

    }

    public void Init()
    {
        retCode = new String(new char[7]); //返回码
        bankDate =  new String(new char[8]);
        protocolNo = new String(new char[20]);
        account = new String(new char[20]);
        accountArea = new String(new char[4]);
        curCode = new String(new char[3]);
        amount = new String(new char[20]);
        transDetail = new String(new char[80]); //交易金额交易信息描述
    }


    public String getAccount() {
        return account;
    }

    public void setAccount(String account) throws Exception {
        account = getFillStr(this.account, account);
        this.account = account;
    }

    public String getAccountArea() {
        return accountArea;
    }

    public void setAccountArea(String accountArea) throws Exception {
        accountArea = getFillStr(this.accountArea, accountArea);
        this.accountArea = accountArea;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) throws Exception {
        amount = getFillStr(this.amount, amount);
        this.amount = amount;
    }

    public String getBankDate() {
        return bankDate;
    }

    public void setBankDate(String bankDate) throws Exception {
        bankDate = getFillStr(this.bankDate, bankDate);
        this.bankDate = bankDate;
    }

    public String getCurCode() {
        return curCode;
    }

    public void setCurCode(String curCode) throws Exception {
        curCode = getFillStr(this.curCode, curCode);
        this.curCode = curCode;
    }


    public String getProtocolNo() {
        return protocolNo;
    }

    public void setProtocolNo(String protocolNo) throws Exception {
        protocolNo = getFillStr(this.protocolNo, protocolNo);
        this.protocolNo = protocolNo;
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) throws Exception {
        retCode = getFillStr(this.retCode, retCode);
        this.retCode = retCode;
    }

    public String getTransDetail() {
        return transDetail;
    }

    public void setTransDetail(String transDetail) throws Exception {
        transDetail = getFillStr(this.transDetail, transDetail);
        this.transDetail = transDetail;
    }

    public String getPackage()
    {
        StringBuffer sb = new StringBuffer();
        sb.append(retCode);
        sb.append(bankDate);
        sb.append(protocolNo);
        sb.append(account);
        sb.append(accountArea);
        sb.append(curCode);
        sb.append(amount);
        sb.append(transDetail);
        return sb.toString();
    }



}
