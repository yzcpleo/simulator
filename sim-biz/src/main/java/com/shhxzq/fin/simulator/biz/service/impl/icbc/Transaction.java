package com.shhxzq.fin.simulator.biz.service.impl.icbc;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by houjiagang on 16/7/26.
 */
@Setter
@Getter
public class Transaction {

    @XStreamAlias("CorpAccNo")
    private String corpAccNo;

    @XStreamAlias("AccNo")
    private String accNo;

    @XStreamAlias("SupType")
    private String supType;

    @XStreamAlias("AccName")
    private String accName;

    @XStreamAlias("IdType")
    private String idType;

    @XStreamAlias("IdCode")
    private String idCode;

    @XStreamAlias("MobilePhone")
    private String mobilePhone;

    @XStreamAlias("CorpNo")
    private String corpNo;

    @XStreamAlias("PersonNo")
    private String personNo;

    @XStreamAlias("DeadLine")
    private String deadLine;

    @XStreamAlias("ReqReserved1")
    private String reqReserved1;

    @XStreamAlias("ReqReserved2")
    private String reqReserved2;

    @XStreamAlias("ReqReserved3")
    private String reqReserved3;

    @XStreamAlias("ReqReserved4")
    private String reqReserved4;

    @XStreamAlias("BankSeq")
    private String bankSeq;

    @XStreamAlias("AccType")
    private String accType;

    @XStreamAlias("AreaCode")
    private String areaCode;

    @XStreamAlias("SMSSendFlag")
    private String smsSendFlag;

    @XStreamAlias("SMSSendNo")
    private String smsSendNo;

    @XStreamAlias("RepReserved1")
    private String repReserved1;

    @XStreamAlias("RepReserved2")
    private String repReserved2;

    @XStreamAlias("RepReserved3")
    private String repReserved3;

    @XStreamAlias("RepReserved4")
    private String repReserved4;

    @XStreamAlias("VeriCode")
    private String veriCode;

    @XStreamAlias("Pcode")
    private String pcode;

    @XStreamAlias("UserAcc")
    private String userAcc;

    @XStreamAlias("UserName")
    private String userName;

    @XStreamAlias("TotalAmount")
    private String totalAmount;

    @XStreamAlias("CurrType")
    private String currType;

    @XStreamAlias("Summary")
    private String summary;

    @XStreamAlias("UserInfo1")
    private String userinfo1;

    @XStreamAlias("UserInfo2")
    private String userinfo2;

    @XStreamAlias("UserInfo3")
    private String userinfo3;

    @XStreamAlias("UserInfo4")
    private String userinfo4;

    @XStreamAlias("UserInfo5")
    private String userinfo5;

    @XStreamAlias("UserInfo6")
    private String userinfo6;

    @XStreamAlias("UserInfo7")
    private String userinfo7;

    @XStreamAlias("UserInfo8")
    private String userinfo8;

    @XStreamAlias("UserInfo9")
    private String userinfo9;

    @XStreamAlias("UserInfo10")
    private String userinfo10;

    @XStreamAlias("HostDate")
    private String hostDate;

    @XStreamAlias("BeginDate")
    private String beginDate;

    @XStreamAlias("EndDate")
    private String endDate;

    @XStreamAlias("QryfSeqno")
    private String qryfSeqno;

    @XStreamAlias("QryBankSeq")
    private String qryBankSeq;

    @XStreamAlias("NextTag")
    private String nextTag;

    @XStreamAlias("TotalNum")
    private String totalNum;

    @XStreamAlias("OnlBatF")
    private String onlBatF;

    @XStreamAlias("SettleMode")
    private String settleMode;

    @XStreamAlias("TotalAmt")
    private String totalAmt;

    @XStreamAlias("SignTime")
    private String signTime;

    @XStreamAlias("QrySerialNo")
    private String qrySerialNo;

    @XStreamAlias("ResultType")
    private String resultType;

    @XStreamAlias("BeginTime")
    private String beginTime;

    @XStreamAlias("EndTime")
    private String EndTime;

}
