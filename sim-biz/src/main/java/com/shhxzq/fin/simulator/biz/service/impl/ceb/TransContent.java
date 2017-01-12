package com.shhxzq.fin.simulator.biz.service.impl.ceb;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;
import lombok.Setter;


/**
 * Created by houjiagang on 16/9/7.
 */
@Setter
@Getter
public class TransContent {

    @XStreamAlias("ReturnCode")
    private String returnCode;

    @XStreamAlias("ReturnMsg")
    private String returnMsg;

    @XStreamAlias("ReturnNote")
    private String returnNote;

    @XStreamAlias("ReqData")
    private ReqData ReqData;

    @XStreamAlias("RespData")
    private ReqData respData;

    @Setter
    @Getter
    public static class ReqData{

        @XStreamAlias("ClientPatchID")
        private String clientPatchID;

        @XStreamAlias("transferType")
        private String transferType;

        @XStreamAlias("accountNo")
        private String accountNo;

        @XStreamAlias("toAccountName")
        private String toAccountName;

        @XStreamAlias("toAccountNo")
        private String toAccountNo;

        @XStreamAlias("toBank")
        private String toBank;

        @XStreamAlias("amount")
        private String amount;

        @XStreamAlias("toLocation")
        private String toLocation;

        @XStreamAlias("clientSignature")
        private String clientSignature;

        @XStreamAlias("checkNo")
        private String checkNo;

        @XStreamAlias("checkPassword")
        private String checkPassword;

        @XStreamAlias("note")
        private String note;

        @XStreamAlias("noteOther")
        private String noteOther;

        @XStreamAlias("bankNo")
        private String bankNo;

        @XStreamAlias("isUrgent")
        private String isUrgent;

        @XStreamAlias("cellphone")
        private String cellphone;

        @XStreamAlias("perOrEnt")
        private String perOrEnt;

        @XStreamAlias("IsAudit")
        private String isAudit;

        @XStreamAlias("matchRule")
        private String matchRule;

        @XStreamAlias("transDateTime")
        private String transDateTime;

        @XStreamAlias("respond1")
        private String respond1;

        @XStreamAlias("respond2")
        private String respond2;

        @XStreamAlias("respond3")
        private String respond3;

        @XStreamAlias("respond4")
        private String respond4;

        @XStreamAlias("respond5")
        private String respond5;

        @XStreamAlias("ClientBchID")
        private String clientBchID;

        @XStreamAlias("ClientPchID")
        private String clientPchID;

        @XStreamAlias("NowFlag")
        private String nowFlag;

        @XStreamAlias("TranferFlag")
        private String tranferFlag;

    }
}
