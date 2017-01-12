package com.shhxzq.fin.simulator.biz.service.impl.cmb;


/**
 * <p>Title: 资金处理系统</p>
 * <p>
 * <p>Description: </p>
 * <p>
 * <p>Copyright: Copyright (c) 2009</p>
 */
public class PkgBodyCMYZRep extends PkgCommon {
    private String retCode; //返回码
    private String bankDate; //银行帐务日期
    private String protocolNo; //客户号
    private String acctno; //帐号
    private String areaCode; //帐户地区
    private String curCode; //币种
    private String amount; //交易金额
    private String transDetail; //交易金额交易信息描述


    private Integer pkgBodyLen;

    public PkgBodyCMYZRep() {
        Init();
    }

    public PkgBodyCMYZRep(String inputStr) throws Exception {
        if (inputStr == null) {
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
        bankDate = getFieldValue(bankDate);//银行帐务日期
        protocolNo = getFieldValue(protocolNo);//客户号
        acctno = getFieldValue(acctno);//帐号
        areaCode = getFieldValue(areaCode);//帐户地区
        curCode = getFieldValue(curCode);//币种
        amount = getFieldValue(amount);//交易金额
        transDetail = getFieldValue(transDetail);//交易金额交易信息描述


    }

    public void Init() {
        retCode = new String(new char[7]); //返回码
        bankDate = new String(new char[8]); //银行帐务日期
        protocolNo = new String(new char[20]); //客户号
        acctno = new String(new char[20]); //帐号
        areaCode = new String(new char[4]); //帐户地区
        curCode = new String(new char[3]); //币种币种币种
        amount = new String(new char[20]); //交易金额交易金额
        transDetail = new String(new char[80]); //交易金额交易信息描述
    }

    public String getAmount() {
        return amount;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public String getBankDate() {
        return bankDate;
    }

    public String getAcctno() {
        return acctno;
    }

    public String getCurCode() {
        return curCode;
    }

    public String getProtocolNo() {
        return protocolNo;
    }

    public String getRetCode() {
        return retCode;
    }

    public String getTransDetail() {
        return transDetail;
    }

    /**
     * 获取包体长度
     *
     * @return Integer
     */
    public Integer getPkgBodyLen() {
        int iPkgBodyLen = 0;

        //返回码
        if (retCode != null) {
            iPkgBodyLen += retCode.getBytes().length;
        }

        //银行帐务日期
        if (bankDate != null) {
            iPkgBodyLen += bankDate.getBytes().length;
        }

        //客户号
        if (protocolNo != null) {
            iPkgBodyLen += protocolNo.getBytes().length;
        }

        //帐号
        if (acctno != null) {
            iPkgBodyLen += acctno.getBytes().length;
        }

        //帐户地区
        if (areaCode != null) {
            iPkgBodyLen += areaCode.getBytes().length;
        }

        //币种
        if (curCode != null) {
            iPkgBodyLen += curCode.getBytes().length;
        }

        //交易金额
        if (amount != null) {
            iPkgBodyLen += amount.getBytes().length;
        }

        //交易信息
        if (transDetail != null) {
            iPkgBodyLen += transDetail.getBytes().length;
        }

        pkgBodyLen = new Integer(iPkgBodyLen);
        return pkgBodyLen;
    }


    public void setAmount(String amount) throws Exception {
        amount = getFillStr(this.amount, amount);
        this.amount = amount;
    }

    public void setAreaCode(String areaCode) throws Exception {
        areaCode = getFillStr(this.areaCode, areaCode);
        this.areaCode = areaCode;
    }

    public void setBankDate(String bankDate) throws Exception {
        bankDate = getFillStr(this.bankDate, bankDate);
        this.bankDate = bankDate;
    }

    public void setAcctno(String acctno) throws Exception {
        acctno = getFillStr(this.acctno, acctno);
        this.acctno = acctno;
    }

    public void setCurCode(String curCode) throws Exception {
        curCode = getFillStr(this.curCode, curCode);
        this.curCode = curCode;
    }

    public void setRetCode(String retCode) throws Exception {
        retCode = getFillStr(this.retCode, retCode);
        this.retCode = retCode;
    }

    public void setTransDetail(String transDetail) throws Exception {
        transDetail = getFillStr(this.transDetail, transDetail);
        this.transDetail = transDetail;
    }

    public void setProtocolNo(String protocolNo) throws Exception {
        protocolNo = getFillStr(this.protocolNo, protocolNo);
        this.protocolNo = protocolNo;
    }


    public String getPackage()
    {
        StringBuffer sb = new StringBuffer();
        sb.append(retCode);
        sb.append(bankDate);
        sb.append(protocolNo);
        sb.append(acctno);
        sb.append(areaCode);
        sb.append(curCode);
        sb.append(amount);
        sb.append(transDetail);
        return sb.toString();
    }


}
