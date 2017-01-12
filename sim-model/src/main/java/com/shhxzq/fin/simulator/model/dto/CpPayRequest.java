package com.shhxzq.fin.simulator.model.dto;

import lombok.Data;

/**
 * @author kangyonggan
 * @since 16/5/19
 */
@Data
public class CpPayRequest {

    private String merId;

    private String transDate;

    private String orderNo;

    private String transType;

    private String openBankId;

    private String cardType;

    private String cardNo;

    private String usrName;

    private String certType;

    private String certId;

    private String curyId;

    private String transAmt;

    private String purpose;

    private String priv1;

    private String version;

    private String gateId;

    private String termType;

    private String chkValue;

}
