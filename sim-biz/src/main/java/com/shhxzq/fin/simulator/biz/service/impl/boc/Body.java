package com.shhxzq.fin.simulator.biz.service.impl.boc;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by houjiagang on 16/8/3.
 */
@Setter
@Getter
public class Body {

    @XStreamAlias("cardType")
    private String cardType;

    @XStreamAlias("cardNo")
    private String cardNo;

    @XStreamAlias("amount")
    private String amount;

    @XStreamAlias("tranChannel")
    private String tranChannel;

    @XStreamAlias("channel")
    private String channel;

    @XStreamAlias("mobileNo")
    private String mobileNo;

    @XStreamAlias("custName")
    private String custName;

    @XStreamAlias("identityType")
    private String identityType;

    @XStreamAlias("identityNo")
    private String identityNo;

    @XStreamAlias("mchtSmsCode")
    private String mchtSmsCode;

    @XStreamAlias("uniqueCode")
    private String uniqueCode;

    @XStreamAlias("smsCode")
    private String smsCode;

    @XStreamAlias("traceNo")
    private String traceNo;

    @XStreamAlias("currency")
    private String currency;

    @XStreamAlias("subMerchantCode")
    private String subMerchantCode;

    @XStreamAlias("subMerchantName")
    private String subMerchantName;

    @XStreamAlias("purchaseCode")
    private String purchaseCode;

    @XStreamAlias("purchaseNote")
    private String purchaseNote;

    @XStreamAlias("merchantNo")
    private String merchantNo;

    @XStreamAlias("recvTime")
    private String recvTime;

    @XStreamAlias("tranTime")
    private String tranTime;

    @XStreamAlias("tranCode")
    private String tranCode;

    @XStreamAlias("tranStatus")
    private String tranStatus;

}

