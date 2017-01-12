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
public class SHYQResponse {

    @XStreamAlias("opRep")
    private OpRep opRep;


    @Setter
    @Getter
    @XStreamAlias("opRep")
    public static class OpRep {

        @XStreamAlias("serialNo")
        private String serialNo;

        @XStreamAlias("retCode")
        private String retCode;

        @XStreamAlias("errMsg")
        private String errMsg;

        @XStreamAlias("opResult")
        private OpResult opResult;

    }

    @Setter
    @Getter
    @XStreamAlias("opResult")
    public static class OpResult {

        @XStreamAlias("T24S")
        private String T24S;

        @XStreamAlias("T24D")
        private String T24D;


        @XStreamAlias("T24C")
        private String T24C;

        @XStreamAlias("T24M")
        private String T24M;

        @XStreamAlias("CNAS")
        private String CNAS;

        @XStreamAlias("CNAC")
        private String CNAC;

        @XStreamAlias("CNAR")
        private String CNAR;

        @XStreamAlias("RECO")
        private String RECO;

        @XStreamAlias("REMG")
        private String REMG;

    }

}
