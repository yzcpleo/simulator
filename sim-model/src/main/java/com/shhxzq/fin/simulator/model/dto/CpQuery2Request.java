package com.shhxzq.fin.simulator.model.dto;

import lombok.Data;

/**
 * @author kangyonggan
 * @since 16/5/19
 */
@Data
public class CpQuery2Request {

    private String merId;

    private String transType;

    private String orderNo;

    private String transDate;

    private String version;

    private String priv1;

    private String chkValue;

}
