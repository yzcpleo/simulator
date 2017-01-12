package com.shhxzq.fin.simulator.biz.service.impl.sh;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by tangqianwen on 16/11/25.
 */

@Setter
@Getter
@XStreamAlias("BOSEBankData")
public class SHYQRequest {
    @XStreamAlias("opReq")
    private OpReq opReq;


    @Setter
    @Getter
    public static class OpReq {
        @XStreamAlias("serialNo")
        private String serialNo;

        @XStreamAlias("reqTime")
        private String reqTime;

        @XStreamAlias("ReqParam")
        private ReqParam ReqParam;
    }


    @Setter
    @Getter
    public static class ReqParam {

        @XStreamAlias("ACNO")
        private String ACNO;

        @XStreamAlias("OPAC")
        private String OPAC;

        @XStreamAlias("TRAM")
        private String TRAM;

        @XStreamAlias("NAME")
        private String NAME;

        @XStreamAlias("USAG")
        private String USAG;

        @XStreamAlias("REMK")
        private String REMK;

        @XStreamAlias("COSE")
        private String COSE;


        @XStreamAlias("OSNO")
        private String OSNO;
    }
}
