package com.shhxzq.fin.simulator.biz.service.impl.icbc;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by houjiagang on 16/7/26.
 */
@Setter
@Getter
@XStreamAlias("rd")
public class Query {

    @XStreamAlias("BankSeq")
    private String bankSeq;

    @XStreamAlias("CurfSeqno")
    private String curfSeqno;

    @XStreamAlias("Pcode")
    private String pcode;

    @XStreamAlias("UserAcc")
    private String userAcc;

    @XStreamAlias("UserName")
    private String userName;

    @XStreamAlias("IdType")
    private String idType;

    @XStreamAlias("IdCode")
    private String idCode;

    @XStreamAlias("MobilePhone")
    private String mobilePhone;

    @XStreamAlias("TotalAmount")
    private String totalAmount;

    @XStreamAlias("CurrType")
    private String currType;

    @XStreamAlias("Summary")
    private String summary;

    @XStreamAlias("Status")
    private String status;

    @XStreamAlias("BankRem")
    private String bankRem;

    @XStreamAlias("RepReserved1")
    private String repReserved1;

    @XStreamAlias("RepReserved2")
    private String repReserved2;

    @XStreamAlias("RepReserved3")
    private String repReserved3;

    @XStreamAlias("RepReserved4")
    private String repReserved4;

    @XStreamAlias("iSeqno")
    private String iSeqno;

    @XStreamAlias("ReimburseNo")
    private String reimburseNo;

    @XStreamAlias("ReimburseNum")
    private String reimburseNum;

    @XStreamAlias("StartDate")
    private String startDate;

    @XStreamAlias("StartTime")
    private String startTime;

    @XStreamAlias("PayType")
    private String PayType;

    @XStreamAlias("PayAccNo")
    private String PayAccNo;

    @XStreamAlias("PayAccNameCN")
    private String PayAccNameCN;

    @XStreamAlias("PayAccNameEN")
    private String PayAccNameEN;

    @XStreamAlias("RecAccNo")
    private String RecAccNo;

    @XStreamAlias("RecAccNameCN")
    private String RecAccNameCN;

    @XStreamAlias("RecAccNameEN")
    private String RecAccNameEN;

    @XStreamAlias("SysIOFlg")
    private String SysIOFlg;

    @XStreamAlias("IsSameCity")
    private String IsSameCity;

    @XStreamAlias("RecICBCCode")
    private String RecICBCCode;

    @XStreamAlias("RecCityName")
    private String RecCityName;

    @XStreamAlias("RecBankNo")
    private String RecBankNo;

    @XStreamAlias("RecBankName")
    private String RecBankName;

    @XStreamAlias("CurrType")
    private String CurrType;

    @XStreamAlias("PayAmt")
    private String PayAmt;

    @XStreamAlias("UseCode")
    private String UseCode;

    @XStreamAlias("UseCN")
    private String UseCN;

    @XStreamAlias("EnSummary")
    private String EnSummary;

    @XStreamAlias("PostScript")
    private String PostScript;

    @XStreamAlias("Summary")
    private String Summary;

    @XStreamAlias("Ref")
    private String Ref;

    @XStreamAlias("Oref")
    private String Oref;

    @XStreamAlias("ERPSqn")
    private String ERPSqn;

    @XStreamAlias("BusCode")
    private String BusCode;

    @XStreamAlias("ERPcheckno")
    private String ERPcheckno;

    @XStreamAlias("CrvouhType")
    private String CrvouhType;

    @XStreamAlias("CrvouhName")
    private String CrvouhName;

    @XStreamAlias("CrvouhNo")
    private String CrvouhNo;

    @XStreamAlias("ReqReserved3")
    private String ReqReserved3;

    @XStreamAlias("ReqReserved4")
    private String ReqReserved4;

    @XStreamAlias("OrderNo")
    private String orderNo;

    @XStreamAlias("Result")
    private String Result;

    @XStreamAlias("iRetCode")
    private String iRetCode;

    @XStreamAlias("iRetMsg")
    private String iRetMsg;

    @XStreamAlias("QryiSeqno")
    private String qryiSeqno;

    @XStreamAlias("QryOrderNo")
    private String qryOrderNo;

    @XStreamAlias("instrRetCode")
    private String instrRetCode;

    @XStreamAlias("instrRetMsg")
    private String instrRetMsg;

    @XStreamAlias("BankRetTime")
    private String bankRetTime;

    @XStreamAlias("SerialNo")
    private String serialNo;

    @XStreamAlias("fSeqno")
    private String fSeqno;

    @XStreamAlias("OnlBatF")
    private String onlBatF;

    @XStreamAlias("SettleMode")
    private String settleMode;

    @XStreamAlias("AccNo")
    private String accNo;

    @XStreamAlias("AccNameCN")
    private String accNameCN;
}
