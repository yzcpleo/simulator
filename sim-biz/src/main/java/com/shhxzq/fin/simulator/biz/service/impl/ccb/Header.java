package com.shhxzq.fin.simulator.biz.service.impl.ccb;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by houjiagang on 16/7/21.
 */

@Getter
@Setter
public class Header {

    @XStreamAlias("txcode")
    private String txcode;

    @XStreamAlias("txseq")
    private String txseq;

    @XStreamAlias("txdate")
    private String txdate;

    @XStreamAlias("txtime")
    private String txtime;

    @XStreamAlias("tminf")
    private String tminf;

    @XStreamAlias("txsign")
    private String txsign;

    @XStreamAlias("tran_response")
    private TranResponse tranResponse;

    @Setter
    @Getter
    public static class TranResponse{

        @XStreamAlias("status")
        private String status;

        @XStreamAlias("code")
        private String code;

        @XStreamAlias("desc")
        private String desc;
    }

}
