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
 * <p>Remark: 商户启用电子协议 商户请求</p>
 *

 */
public class PkgBodyCMQYReq extends PkgCommon
{
    private String custCode;//客户号
    private String fundAcct;//基金账户号
    private String acctno;//帐号
    private String areaCode;//帐户地区
    private String subCorpNo;//子商户号
    private String memo;//备注

    public PkgBodyCMQYReq()
    {
        Init();
    }

    public PkgBodyCMQYReq(String inputStr) throws Exception {

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

        custCode = getFieldValue(custCode);
        fundAcct = getFieldValue(fundAcct);
        acctno = getFieldValue(acctno);
        areaCode = getFieldValue(areaCode);
        subCorpNo = getFieldValue(subCorpNo);
        memo = getFieldValue(memo);


    }


    public void Init()
    {
        custCode = new String(new char[20]); //客户号(商户系统的客户编号，如电话号码，手机号码等)
        fundAcct = new String(new char[32]);//基金账号
        acctno = new String(new char[20]); //帐号(招行帐号)
        areaCode = new String(new char[4]); //帐户地区
        subCorpNo = new String(new char[5]); //客户在商户机构
        memo = new String(new char[40]); //备注
    }

    public void setCustCode(String custCode)  throws Exception
    {
        custCode = getFillStr(this.custCode, custCode);

        this.custCode = custCode;
    }

    public void setAcctno(String acctno) throws Exception
    {
        acctno = getFillStr(this.acctno, acctno);

        this.acctno = acctno;
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

    public void setMemo(String memo) throws Exception
    {
        memo = getFillStr(this.memo, memo);

        this.memo = memo;
    }

    public String getCustCode()
    {
        return custCode;
    }

    public String getAcctno()
    {
        return acctno;
    }

    public String getAreaCode()
    {
        return areaCode;
    }

    public String getSubCorpNo()
    {
        return subCorpNo;
    }

    public String getRemark()
    {
        return memo;
    }

    /**
     * 获取包体
     * @return String
     */
    public String getPackage()
    {
        StringBuffer sb = new StringBuffer();
        sb.append(custCode);
        sb.append(fundAcct);
        sb.append(acctno);
        sb.append(areaCode);
        sb.append(subCorpNo);
        sb.append(memo);
        return sb.toString();
    }

	public String getFundAcct() {
		return fundAcct;
	}

	public void setFundAcct(String fundAcct) throws Exception {
		
		fundAcct = getFillStr(this.fundAcct, fundAcct); 
		this.fundAcct = fundAcct;
	}

	public String getMemo() {
		return memo;
	}
}
