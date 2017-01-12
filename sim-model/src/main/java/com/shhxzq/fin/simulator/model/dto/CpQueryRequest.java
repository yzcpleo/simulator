package com.shhxzq.fin.simulator.model.dto;

import lombok.Data;

/**
 * @author kangyonggan
 * @since 16/5/19
 */
@Data
public class CpQueryRequest {

    private String merId;

    private String merDate;

    private String merSeqId;

    private String version;

    private String chkValue;

    private String signFlag;

}
