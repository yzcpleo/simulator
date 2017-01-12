package com.shhxzq.fin.simulator.biz.service.impl.ccb.socket;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by houjiagang on 16/8/8.
 */
@Setter
@Getter
@XStreamAlias("TX")
public class Tx {

    @XStreamAlias("REQUEST_SN")
    private String requestSn;

    @XStreamAlias("CUST_ID")
    private String custId;

    @XStreamAlias("USER_ID")
    private String userId;

    @XStreamAlias("PASSWORD")
    private String password;

    @XStreamAlias("TX_CODE")
    private String txCode;

    @XStreamAlias("LANGUAGE")
    private String language;

    @XStreamAlias("SIGN_INFO")
    private String signInfo;

    @XStreamAlias("SIGNCERT")
    private String signcert;

    @XStreamAlias("TX_INFO")
    private Body body;

    @XStreamAlias("RETURN_CODE")
    private String returnCode;

    @XStreamAlias("RETURN_MSG")
    private String returnMsg;


}
