package com.shhxzq.fin.simulator.biz.service.impl.cmb;



/**
 * <p>Title: 资金处理系统</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2009</p>

 */
public class PkgBodyCMQXReq extends PkgCommon
{
    private String custCode; //客户号(商户系统的客户编号，如电话号码，手机号码等)
    private String acctno; //帐号(招行帐号)
    private String areaCode; //帐户地区
    private String subCorpNo; //子商务号/产品类型
    private String memo; //备用


    public PkgBodyCMQXReq()
    {
        Init();
    }


    public PkgBodyCMQXReq(String inputStr){
        Init();
        int lengthCount = 0;

        lengthCount += custCode.length();
        if(inputStr.length() > lengthCount){
            custCode = inputStr.substring(0, lengthCount);
        }

        lengthCount += acctno.length();
        if(inputStr.length() > lengthCount){
            acctno = inputStr.substring(lengthCount - acctno.length(), lengthCount);
        }

        lengthCount += areaCode.length();
        if(inputStr.length() > lengthCount){
            areaCode = inputStr.substring(lengthCount - areaCode.length(), lengthCount);
        }

        lengthCount += subCorpNo.length();
        if(inputStr.length() > lengthCount){
            subCorpNo = inputStr.substring(lengthCount - subCorpNo.length(), lengthCount);
        }

        lengthCount += memo.length();
        if(inputStr.length() > lengthCount){
            memo = inputStr.substring(lengthCount - memo.length(), lengthCount);
        }

    }

    public void Init()
    {
        custCode = new String(new char[20]); //客户号(商户系统的客户编号，如电话号码，手机号码等)
        acctno = new String(new char[20]); //帐号(招行帐号)
        areaCode = new String(new char[4]); //帐户地区
        subCorpNo = new String(new char[10]); //客户在商户机构
        memo = new String(new char[40]); //备用
    }

    public String getAcctno()
    {
        return acctno;
    }


    public String getCustCode()
    {
        return custCode;
    }

    public String getMemo()
    {
        return memo;
    }

    public String getAreaCode()
    {
        return areaCode;
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

    public void setCustCode(String custCode) throws Exception
    {
        custCode = getFillStr(this.custCode, custCode);

        this.custCode = custCode;
    }

    public void setMemo(String memo) throws Exception
    {
        memo = getFillStr(this.memo, memo);

        this.memo = memo;
    }

    public void setAreaCode(String areaCode) throws Exception
    {
        areaCode = getFillStr(this.areaCode, areaCode);

        this.areaCode = areaCode;
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
        sb.append(custCode);
        sb.append(acctno);
        sb.append(areaCode);
        sb.append(subCorpNo);
        sb.append(memo);
        return sb.toString();
    }
}
