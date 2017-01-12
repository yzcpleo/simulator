package com.shhxzq.fin.simulator.biz.service.impl.pab;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by houjiagang on 2016/11/1.
 */
@Setter
@Getter
@XStreamAlias("Result")
public class Result {

    //400101-鉴权
    @XStreamAlias("AccountNo")
    private String accountNo;

    @XStreamAlias("AccountName")
    private String accountName;

    @XStreamAlias("CertType")
    private String certType;

    @XStreamAlias("CertNo")
    private String certNo;

    @XStreamAlias("Mobile")
    private String mobile;

    //400101 response
    @XStreamAlias("Flag")
    private String flag;

    @XStreamAlias("Desc")
    private String desc;


    //4047
    @XStreamAlias("AGREE_NO")
    private String agreeNo;

    @XStreamAlias("BusiType")
    private String busiType;

    @XStreamAlias("PayType")
    private String payType;

    @XStreamAlias("Currency")
    private String currency;

    @XStreamAlias("othBankFlag")
    private String OthBankFlag;

    @XStreamAlias("SrcAccNo")
    private String srcAccNo;

    @XStreamAlias("TotalNum")
    private String totalNum;

    @XStreamAlias("TotalAmount")
    private String totalAmount;

    @XStreamAlias("SettleType")
    private String settleType;


    @XStreamAlias("HOResultSet4047R")
    private HOResultSet4047R hoResultSet4047R;


    //4047 response
    @XStreamAlias("BussFlowNo")
    private String bussFlowNo;

    @XStreamAlias("list")
    private ResultList list;


    //4048
    @XStreamAlias("ThirdVoucher")
    private String thirdVoucher;

    //4048 response

    @XStreamAlias("BStt")
    private String bstt;



    //4005
    @XStreamAlias("OrigThirdLogNo")
    private String origThirdLogNo;

    @XStreamAlias("OrigThirdVoucher")
    private String origThirdVoucher;

    @XStreamAlias("OrigFrontLogNo")
    private String origFrontLogNo;

    //4005 response
    @XStreamAlias("Yhcljg")
    private String Yhcljg;

    @XStreamAlias("Fee")
    private String fee;

    @XStreamAlias("Stt")
    private String stt;

    @XStreamAlias("IsBack")
    private String isBack;

    @XStreamAlias("BackRem")
    private String backRem;

    @XStreamAlias("TransBsn")
    private String transBsn;

    @XStreamAlias("submitTime")
    private String submitTime;

    @XStreamAlias("AccountDate")
    private String accountDate;

    @XStreamAlias("hostErrorCode")
    private String hostErrorCode;

    @XStreamAlias("kType")
    private String kType;

    @XStreamAlias("dType")
    private String dType;

    @XStreamAlias("subBatchNo")
    private String subBatchNo;

    @XStreamAlias("OutAcctBankName")
    private String outAcctBankName;

    //4004
    @XStreamAlias("CstInnerFlowNo")
    private String cstInnerFlowNo;

    @XStreamAlias("CcyCode")
    private String ccyCode;

    @XStreamAlias("OutAcctNo")
    private String outAcctNo;

    @XStreamAlias("OutAcctName")
    private String outAcctName;

    @XStreamAlias("OutAcctAddr")
    private String outAcctAddr;

    @XStreamAlias("InAcctBankNode")
    private String inAcctBankNode;

    @XStreamAlias("InAcctRecCode")
    private String inAcctRecCode;

    @XStreamAlias("InAcctNo")
    private String inAcctNo;

    @XStreamAlias("InAcctName")
    private String inAcctName;

    @XStreamAlias("InAcctBankName")
    private String inAcctBankName;

    @XStreamAlias("InAcctProvinceCode")
    private String inAcctProvinceCode;

    @XStreamAlias("InAcctCityName")
    private String inAcctCityName;

    @XStreamAlias("TranAmount")
    private String tranAmount;

    @XStreamAlias("UseEx")
    private String useEx;

    @XStreamAlias("UnionFlag")
    private String unionFlag;

    @XStreamAlias("SysFlag")
    private String sysFlag;

    @XStreamAlias("AddrFlag")
    private String addrFlag;

    @XStreamAlias("MainAcctNo")
    private String mainAcctNo;

    @XStreamAlias("zdJType")
    private String zdJType;

    @XStreamAlias("zdZType")
    private String zdZType;

    @XStreamAlias("zdBAcc")
    private String zdBAcc;

    @XStreamAlias("ProxyPayName")
    private String proxyPayName;

    @XStreamAlias("ProxyPayAcc")
    private String proxyPayAcc;

    @XStreamAlias("ProxyPayBankName")
    private String proxyPayBankName;

    @XStreamAlias("InIDType")
    private String inIDType;

    @XStreamAlias("InIDNo")
    private String inIDNo;

    @XStreamAlias("BFJTranType")
    private String BFJTranType;

    @XStreamAlias("BFJBizType")
    private String BFJBizType;

    //4004 response
    @XStreamAlias("FrontLogNo")
    private String frontLogNo;

    @XStreamAlias("Fee1")
    private String fee1;

    @XStreamAlias("Fee2")
    private String fee2;

    @XStreamAlias("SOA_VOUCHER")
    private String SOA_VOUCHER;

    @XStreamAlias("hostFlowNo")
    private String hostFlowNo;

    @XStreamAlias("hostTxDate")
    private String hostTxDate;

    @XStreamAlias("AmountCode")
    private String amountCode;









    @Setter
    @Getter
    public static class HOResultSet4047R{

        @XStreamAlias("SThirdVoucher")
        private String sThirdVoucher;

        @XStreamAlias("CstInnerFlowNo")
        private String cstInnerFlowNo;

        @XStreamAlias("OthAreaFlag")
        private String othAreaFlag;

        @XStreamAlias("IdType")
        private String idType;

        @XStreamAlias("IdNo")
        private String idNo;

        @XStreamAlias("OppBankName")
        private String oppBankName;

        @XStreamAlias("OppAccNo")
        private String oppAccNo;

        @XStreamAlias("OppAccName")
        private String oppAccName;

        @XStreamAlias("OppBranchId")
        private String oppBranchId;

        @XStreamAlias("Province")
        private String province;

        @XStreamAlias("City")
        private String city;

        @XStreamAlias("Amount")
        private String amount;

        @XStreamAlias("PostScript")
        private String postScript;

        @XStreamAlias("RemarkFCR")
        private String remarkFCR;

    }


    @Setter
    @Getter
    public static class ResultList{

        @XStreamAlias("SThirdVoucher")
        private String sThirdVoucher;

        @XStreamAlias("CstInnerFlowNo")
        private String cstInnerFlowNo;

        @XStreamAlias("IdType")
        private String idType;

        @XStreamAlias("IdNo")
        private String idNo;

        @XStreamAlias("sttInfo")
        private String sttInfo;

        @XStreamAlias("OppAccNo")
        private String oppAccNo;

        @XStreamAlias("OppAccName")
        private String oppAccName;

        @XStreamAlias("Amount")
        private String amount;

        @XStreamAlias("Fee")
        private String fee;

        @XStreamAlias("stt")
        private String stt;

        @XStreamAlias("PostScript")
        private String postScript;

        @XStreamAlias("RemarkFCR")
        private String remarkFCR;

        @XStreamAlias("TraderCode")
        private String traderCode;

    }


}
