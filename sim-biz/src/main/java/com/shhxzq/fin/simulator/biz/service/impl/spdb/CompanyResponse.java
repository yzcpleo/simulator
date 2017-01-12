package com.shhxzq.fin.simulator.biz.service.impl.spdb;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by houjiagang on 16/7/18.
 */
@XStreamAlias("packet")
public class CompanyResponse {

    @XStreamAlias("head")
    private ResponseHead head;

    @XStreamAlias("body")
    private ResponseBody body;

    public ResponseBody getBody() {
        return body;
    }

    public void setBody(ResponseBody body) {
        this.body = body;
    }

    public ResponseHead getHead() {
        return head;
    }

    public void setHead(ResponseHead head) {
        this.head = head;
    }

    public static class ResponseHead{

        @XStreamAlias("transCode")
        private String transCode;

        @XStreamAlias("signFlag")
        private String signFlag;

        @XStreamAlias("packetID")
        private String packetID;

        @XStreamAlias("timeStamp")
        private String timeStamp;

        @XStreamAlias("returnCode")
        private String returnCode;

        public String getPacketID() {
            return packetID;
        }

        public void setPacketID(String packetID) {
            this.packetID = packetID;
        }

        public String getReturnCode() {
            return returnCode;
        }

        public void setReturnCode(String returnCode) {
            this.returnCode = returnCode;
        }

        public String getSignFlag() {
            return signFlag;
        }

        public void setSignFlag(String signFlag) {
            this.signFlag = signFlag;
        }

        public String getTimeStamp() {
            return timeStamp;
        }

        public void setTimeStamp(String timeStamp) {
            this.timeStamp = timeStamp;
        }

        public String getTransCode() {
            return transCode;
        }

        public void setTransCode(String transCode) {
            this.transCode = transCode;
        }
    }


    public static class ResponseBody{

        @XStreamAlias("signature")
        private String signature;

        public String getSignature() {
            return signature;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }
    }
}
