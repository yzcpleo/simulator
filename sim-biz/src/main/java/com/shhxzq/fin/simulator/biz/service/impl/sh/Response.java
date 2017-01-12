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
@XStreamAlias("CSRes")
public class Response {

    @XStreamAsAttribute()
    @XStreamAlias("id")
    private String id;

    @XStreamAlias("version")
    private String version;

    @XStreamAlias("instId")
    private String instId;

    @XStreamAlias("orderNum")
    private String orderNum;

    @XStreamAlias("cardNo")
    private String cardNo;

    @XStreamAlias("cardCode")
    private String cardCode;

    @XStreamAlias("serialNo")
    private String serialNo;

    @XStreamAlias("date")
    private String date;

    @XStreamAlias("errorCode")
    private String errorCode;

    @XStreamAlias("errorMessage")
    private String errorMessage;

    @XStreamAlias("extension")
    private String extension;

    @XStreamAlias("type")
    private String type;

    @XStreamAlias("status")
    private String status;

    @XStreamAlias("orderRemak")
    private String orderRemak;

    @XStreamAlias("amount")
    private String amount;

    @XStreamAlias("cardType")
    private String cardType;

    @XStreamAlias("charge")
    private String charge;


}
