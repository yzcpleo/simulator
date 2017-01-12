package com.shhxzq.fin.simulator.biz.service.impl.ceb;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by houjiagang on 16/9/6.
 */

@Setter
@Getter
@XStreamAlias("Plain")
public class Plain {

    @XStreamAsAttribute()
    @XStreamAlias("id")
    private String id;

    @XStreamAlias("transId")
    private String transId;

    @XStreamAlias("merId")
    private String merId;

    @XStreamAlias("serialNo")
    private String serialNo;

    @XStreamAlias("date")
    private String date;

    @XStreamAlias("signNo")
    private String signNo;

    @XStreamAlias("amount")
    private String amount;

    @XStreamAlias("currency")
    private String currency;

    @XStreamAlias("url")
    private String url;

    @XStreamAlias("merSecName")
    private String merSecName;

    @XStreamAlias("productInfo")
    private String productInfo;

    @XStreamAlias("clearDate")
    private String cleatDate;

    @XStreamAlias("type")
    private String type;

    @XStreamAlias("charge")
    private String charge;

    @XStreamAlias("originalSerialNo")
    private String originalSerialNo;

    @XStreamAlias("originalDate")
    private String originalDate;

    @XStreamAlias("status")
    private String status;

    @XStreamAlias("errorCode")
    private String errorCode;

    @XStreamAlias("errorMessage")
    private String errorMessage;

    @XStreamAlias("errorDetail")
    private String errorDetail;
}
