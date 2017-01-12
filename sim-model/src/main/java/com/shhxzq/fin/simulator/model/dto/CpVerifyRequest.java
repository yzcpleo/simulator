package com.shhxzq.fin.simulator.model.dto;

import lombok.Data;

/**
 * @author kangyonggan
 * @since 16/5/18
 */
@Data
public class CpVerifyRequest {

    private String fundTransTime;

    private String encMsg;

    private String instuId;

    private String transType;

    private String resv1;

    private String resv2;

    private String resv3;

    private String resv4;

    private String fundMerId;

    private String returnUrl;

    private String signMsg;

    private String version;

}
