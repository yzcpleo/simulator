package com.shhxzq.fin.simulator.biz.service.impl.ccb;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by houjiagang on 16/7/21.
 */
@Setter
@Getter
public class Body {


    @XStreamAlias("request")
    private Request request;

    @XStreamAlias("response")
    private Response response;



    @XStreamAlias("tx_flag")
    private String txFlag;

    @XStreamAlias("shop_no")
    private String shopNo;

    @XStreamAlias("cunt_no")
    private String cuntNo;

    @XStreamAlias("order_no")
    private String orderNo;

    @XStreamAlias("cust_nm")
    private String custNm;

    @XStreamAlias("acct_no")
    private String acctNo;

    @XStreamAlias("curr_cod")
    private String currCod;

    @XStreamAlias("curr_iden")
    private String currIden;

    @XStreamAlias("sms_code")
    private String smsCode;

    @XStreamAlias("amount")
    private String amount;

    @XStreamAlias("sub_shop_info")
    private String subShopInfo;

    @XStreamAlias("sub_shop_name")
    private String subShopName;

    @XStreamAlias("sub_shop_typ")
    private String subShopTyp;

    @XStreamAlias("sub_shop_typnm")
    private String subShopTypnm;

    @XStreamAlias("item_info")
    private String itemInfo;

    @XStreamAlias("item_name")
    private String itemName;

    @XStreamAlias("acct_flag")
    private String acctFlag;

    @XStreamAlias("tx_typ")
    private String txTyp;

    @XStreamAlias("site_nm")
    private String siteNm;

    @XStreamAlias("site_url")
    private String siteUrl;

    @XStreamAlias("ori_date")
    private String oriDate;

    @XStreamAlias("func_cod")
    private String funcCod;

    @XStreamAlias("ori_txseq")
    private String oriTxseq;



    @Setter
    @Getter
    public static class Request{

        @XStreamAlias("tx_flag")
        private String txFlag;

        @XStreamAlias("shop_no")
        private String shopNo;

        @XStreamAlias("cunt_no")
        private String cuntNo;

        @XStreamAlias("cert_typ")
        private String certTyp;

        @XStreamAlias("cert_id")
        private String certId;

        @XStreamAlias("cust_nm")
        private String custNm;

        @XStreamAlias("acct_no")
        private String acctNo;

        @XStreamAlias("mobile")
        private String mobile;

        @XStreamAlias("amount")
        private String amount;

        @XStreamAlias("instl_num")
        private String instlNum;

        @XStreamAlias("acct_flag")
        private String acctFlag;

        @XStreamAlias("sms_code")
        private String smsCode;

    }


    @Setter
    @Getter
    public static class Response{

        @XStreamAlias("valid_flag")
        private String validFlag;

        @XStreamAlias("tx_dt")
        private String txDt;

        @XStreamAlias("bank_flow")
        private String bankFlow;

        @XStreamAlias("curr_cod")
        private String currCod;

        @XStreamAlias("amount")
        private String amount;

        @XStreamAlias("shop_no")
        private String shopNo;

        @XStreamAlias("order_no")
        private String orderNo;

        @XStreamAlias("record")
        private Record record;

        @XStreamAlias("rec_counts")
        private String recCounts;
    }

    @Setter
    @Getter
    public static class Record{

        @XStreamAsAttribute()
        @XStreamAlias("p_type")
        private String pType;

        @XStreamAlias("transtatus")
        private String tranStatus;

        @XStreamAlias("back_trad_date")
        private String backTradDate;

        @XStreamAlias("curr_cod")
        private String currCod;

        @XStreamAlias("amount")
        private String amount;

        @XStreamAlias("ori_txseq")
        private String oriTxseq;
    }
}
