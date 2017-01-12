package com.shhxzq.fin.simulator.model.dto;

import lombok.Data;

/**
 * @author kangyonggan
 * @since 16/5/19
 */
@Data
public class CpRedeemRequest {

    private String merId;

    private String merDate;

    private String merSeqId;

    private String cardNo;

    private String usrName;

    private String openBank;

    private String prov;

    private String city;

    private String transAmt;

    private String purpose;

    private String subBank;

    private String flag;

    private String version;

    private String chkValue;

}
