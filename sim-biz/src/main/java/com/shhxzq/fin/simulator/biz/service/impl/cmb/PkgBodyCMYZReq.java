package com.shhxzq.fin.simulator.biz.service.impl.cmb;


import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

/**
 * <p>Title: 资金处理系统</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2009</p>

 */
public class PkgBodyCMYZReq extends PkgCommon
{
    private String protocolNo; //协议号
    private String acctno; //帐号(招行帐号)
    private String areaCode; //帐户地区
    private String curCode; //币种
    private BigDecimal dAmount; //交易金额
    private String amount; //交易金额
    private String invnm; //户名
    private String idtp; //证件类型
    private String idno; //证件号码
    //private String cardWarrant; //客户在商户机构
    private String subCorpNo; //子商务号/产品类型
    private String transTimes; //缴费期次
    private String transDetail; //缴费信息
    private String memo; //备用

    private BigDecimal DAmount;

    public PkgBodyCMYZReq(String inputStr){
        Init();
        int lengthCount = 0;

        lengthCount += protocolNo.length();
        if(inputStr.length() > lengthCount){
            protocolNo = inputStr.substring(0, lengthCount);
        }

        lengthCount += acctno.length();
        if(inputStr.length() > lengthCount){
            acctno = inputStr.substring(lengthCount - acctno.length(), lengthCount);
        }

        lengthCount += areaCode.length();
        if(inputStr.length() > lengthCount){
            areaCode = inputStr.substring(lengthCount - areaCode.length(), lengthCount);
        }

        lengthCount += curCode.length();
        if(inputStr.length() > lengthCount){
            curCode = inputStr.substring(lengthCount - curCode.length(), lengthCount);
        }

        lengthCount += amount.length();
        if(inputStr.length() > lengthCount){
            amount = inputStr.substring(lengthCount - amount.length(), lengthCount);
        }

        lengthCount += invnm.length();
        if(inputStr.length() > lengthCount){
            invnm = inputStr.substring(lengthCount - invnm.length(), lengthCount);
        }

        lengthCount += idtp.length();
        if(inputStr.length() > lengthCount){
            idtp = inputStr.substring(lengthCount - idtp.length(), lengthCount);
        }

        lengthCount += idno.length();
        if(inputStr.length() > lengthCount){
            idno = inputStr.substring(lengthCount - idno.length(), lengthCount);
        }

        lengthCount += subCorpNo.length();
        if(inputStr.length() > lengthCount){
            subCorpNo = inputStr.substring(lengthCount - subCorpNo.length(), lengthCount);
        }

        lengthCount += transTimes.length();
        if(inputStr.length() > lengthCount){
            transTimes = inputStr.substring(lengthCount - transTimes.length(), lengthCount);
        }

        lengthCount += transDetail.length();
        if(inputStr.length() > lengthCount){
            transDetail = inputStr.substring(lengthCount - transDetail.length(), lengthCount);
        }

        lengthCount += memo.length();
        if(inputStr.length() > lengthCount){
            memo = inputStr.substring(lengthCount - memo.length(), lengthCount);
        }
    }

    public PkgBodyCMYZReq()
    {
        Init();
    }

    public void Init()
    {
	protocolNo = new String(new char[20]); //协议号 
        acctno = new String(new char[20]); //帐号(招行帐号)
        areaCode = new String(new char[4]); //帐户地区
        curCode = new String(new char[3]); //币种币种
        amount = new String(new char[20]); //交易金额
        invnm = new String(new char[60]); //户名
        idtp = new String(new char[1]); //证件类型
        idno = new String(new char[20]); //证件号码
        subCorpNo = new String(new char[10]); //客户在商户机构
        transTimes = new String(new char[10]); //缴费期次
        transDetail = new String(new char[255]); //缴费信息
        memo = new String(new char[40]); //备用
    }

    public String getAcctno()
    {
        return acctno;
    }

    public String getCurCode()
    {
        return curCode;
    }

    public String getProtocolNo()
    {
        return protocolNo;
    }

    public String getIdno()
    {
        return idno;
    }

    public String getIdtp()
    {
        return idtp;
    }

    public String getInvnm()
    {
        return invnm;
    }

    public String getMemo()
    {
        return memo;
    }

    public String getTransDetail()
    {
        return transDetail;
    }

    public String getTransTimes()
    {
        return transTimes;
    }

    public String getAreaCode()
    {
        return areaCode;
    }

    public String getAmount()
    {
        return amount;
    }

    public String getSubCorpNo()
    {
        return subCorpNo;
    }

    public void setAcctno(String acctno) throws Exception
    {
        acctno = getFillStr(this.acctno, acctno);

        this.acctno = acctno;
    }

    public void setCurCode(String curCode) throws Exception
    {
        curCode = getFillStr(this.curCode, curCode);

        this.curCode = curCode;
    }

    public void setProtocolNo(String protocolNo) throws Exception
    {
	protocolNo = getFillStr(this.protocolNo, protocolNo);

        this.protocolNo = protocolNo;
    }

    public void setIdno(String idno) throws Exception
    {
        idno = getFillStr(this.idno, idno);

        this.idno = idno;
    }

    public void setIdtp(String idtp) throws Exception
    {
        idtp = getFillStr(this.idtp, idtp);

        this.idtp = idtp;
    }

    public void setInvnm(String invnm) throws Exception
    {
    	int length = 0;
    	/**
         * 中文UTF8比GBK多一个字节，这里得补回来
         * 举例：关向辉 UTF8 3*3，GBK 2*3 补3个空格
         * 举例：你好123 UTF8 3*2+3，GBK 2*2+3 补2个空格
         * 规则：(getBytes().length-length())/2
         */
        if(StringUtils.isNotBlank(invnm)){
        	length = (invnm.getBytes().length - invnm.length())/2;
        }
    	
        invnm = getFillStr(this.invnm, invnm);

        /**
         * 中文UTF8比GBK多一个字节，这里得补回来
         */
        if(length > 0){
        	invnm = StringUtils.rightPad(invnm, invnm.length() + length, " ");
        }
        
        this.invnm = invnm;
    }

    public void setMemo(String memo) throws Exception
    {
        memo = getFillStr(this.memo, memo);

        this.memo = memo;
    }

    public void setTransDetail(String transDetail) throws Exception
    {
        transDetail = getFillStr(this.transDetail, transDetail);

        this.transDetail = transDetail;
    }

    public void setTransTimes(String transTimes) throws Exception
    {
        transTimes = getFillStr(this.transTimes, transTimes);

        this.transTimes = transTimes;
    }

    public void setAreaCode(String areaCode) throws Exception
    {
        areaCode = getFillStr(this.areaCode, areaCode);

        this.areaCode = areaCode;
    }

    public void setAmount(String amount) throws Exception
    {
        amount = getFillZeroStr(this.amount, amount);

        this.amount = amount;
    }

    public void setDAmount(BigDecimal DAmount) throws Exception
    {
        this.DAmount = DAmount;
    }

    public void setSubCorpNo(String subCorpNo) throws Exception
    {
    	subCorpNo = getFillStr(this.subCorpNo, subCorpNo);
        this.subCorpNo = subCorpNo;
    }

    /**
     * 获取包体
     * @return String
     */
    public String getPackage()
    {
        StringBuffer sb = new StringBuffer();
        sb.append(protocolNo);
        sb.append(acctno);
        sb.append(areaCode);
        sb.append(curCode);
        sb.append(amount);
        sb.append(invnm);
        sb.append(idtp);
        sb.append(idno);
        sb.append(subCorpNo);
        sb.append(transTimes);
        sb.append(transDetail);
        sb.append(memo);
        return sb.toString();
    }
}
