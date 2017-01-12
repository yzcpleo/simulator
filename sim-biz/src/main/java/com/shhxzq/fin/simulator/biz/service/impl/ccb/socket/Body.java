package com.shhxzq.fin.simulator.biz.service.impl.ccb.socket;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by houjiagang on 16/8/8.
 */
@Setter
@Getter
public class Body {

    @XStreamAlias("ACC_NO1")
    private String accNo1;

    @XStreamAlias("BILL_CODE")
    private String billCode;

    @XStreamAlias("ACC_NO2")
    private String accNo2;

    @XStreamAlias("OTHER_NAME")
    private String otherName;

    @XStreamAlias("AMOUNT")
    private String amount;

    @XStreamAlias("USEOF_CODE")
    private String useofCode;

    @XStreamAlias("FLOW_FLAG")
    private String flowFlag;

    @XStreamAlias("UBANK_NO")
    private String ubankNo;

    @XStreamAlias("REM1")
    private String rem1;

    @XStreamAlias("REM2")
    private String rem2;

    @XStreamAlias("CREDIT_NO")
    private String creditNo;

    @XStreamAlias("INDIVIDUAL_NAME1")
    private String individualName1;

    @XStreamAlias("INDIVIDUAL1")
    private String individual1;

    @XStreamAlias("INDIVIDUAL_NAME2")
    private String individualName2;

    @XStreamAlias("INDIVIDUAL2")
    private String individual2;

    @XStreamAlias("REQUEST_SN1")
    private String requestSn1;

    @XStreamAlias("DEAL_RESULT")
    private String dealResult;

    @XStreamAlias("MESSAGE")
    private String message;
}
