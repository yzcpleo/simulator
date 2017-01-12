package com.shhxzq.fin.simulator.biz.service.impl.sh;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by houjiagang on 2016/11/15.
 */
@Setter
@Getter
@XStreamAlias("CSReq")
public class Request {

    @XStreamAsAttribute()
    @XStreamAlias("id")
    private String id;

    @XStreamAlias("version")
    private String version;

    @XStreamAlias("instId")
    private String instId;

    @XStreamAlias("twoId")
    private String twoId;

    @XStreamAlias("orderNum")
    private String orderNum;

    @XStreamAlias("cardCode")
    private String cardCode;

    @XStreamAlias("charge")
    private String charge;

    @XStreamAlias("amount")
    private String amount;

    @XStreamAlias("currency")
    private String currency;

    @XStreamAlias("date")
    private String date;

    @XStreamAlias("name")
    private String name;

    @XStreamAlias("cardNo")
    private String cardNo;

    @XStreamAlias("cardType")
    private String cardType;

    @XStreamAlias("certType")
    private String certType;

    @XStreamAlias("certNo")
    private String certNo;

    @XStreamAlias("mobilePhone")
    private String mobilePhone;

    @XStreamAlias("cvv2")
    private String cvv2;

    @XStreamAlias("validity")
    private String validity;

    @XStreamAlias("KoalB64Cert")
    private String koalB64Cert;

    @XStreamAlias("extension")
    private String extension;

    @XStreamAlias("serialNo")
    private String serialNo;

    @XStreamAlias("type")
    private String type;



}
