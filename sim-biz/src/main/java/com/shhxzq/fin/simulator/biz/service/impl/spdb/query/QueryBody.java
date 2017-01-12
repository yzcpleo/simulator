package com.shhxzq.fin.simulator.biz.service.impl.spdb.query;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by houjiagang on 16/7/19.
 */
public class QueryBody {

    @XStreamAlias("signature")
    private Signature signature;

    public Signature getSignature() {
        return signature;
    }

    public void setSignature(Signature signature) {
        this.signature = signature;
    }

    public static class Signature{

        @XStreamAlias("body")
        private Body body;

        public Body getBody() {
            return body;
        }

        public void setBody(Body body) {
            this.body = body;
        }
    }



    public static class Body{

        @XStreamAlias("elecChequeNo")
        private String elecChequeNo;

        @XStreamAlias("acctNo")
        private String acctNo;

        @XStreamAlias("beginDate")
        private String beginDate;

        @XStreamAlias("endDate")
        private String endDate;

        @XStreamAlias("acceptNo")
        private String acceptNo;

        @XStreamAlias("serialNo")
        private String serialNo;

        @XStreamAlias("queryNumber")
        private String queryNumber;

        @XStreamAlias("beginNumber")
        private String beginNumber;

        @XStreamAlias("singleOrBatchFlag")
        private String singleOrBatchFlag;

        @XStreamAlias("businessNo")
        private String businessNo;

        @XStreamAlias("electronNumber")
        private String electronNumber;

        @XStreamAlias("directMasterID")
        private String directMasterID;

        public String getBusinessNo() {
            return businessNo;
        }

        public void setBusinessNo(String businessNo) {
            this.businessNo = businessNo;
        }

        public String getDirectMasterID() {
            return directMasterID;
        }

        public void setDirectMasterID(String directMasterID) {
            this.directMasterID = directMasterID;
        }

        public String getElectronNumber() {
            return electronNumber;
        }

        public void setElectronNumber(String electronNumber) {
            this.electronNumber = electronNumber;
        }

        public String getAcceptNo() {
            return acceptNo;
        }

        public void setAcceptNo(String acceptNo) {
            this.acceptNo = acceptNo;
        }

        public String getAcctNo() {
            return acctNo;
        }

        public void setAcctNo(String acctNo) {
            this.acctNo = acctNo;
        }

        public String getBeginDate() {
            return beginDate;
        }

        public void setBeginDate(String beginDate) {
            this.beginDate = beginDate;
        }

        public String getBeginNumber() {
            return beginNumber;
        }

        public void setBeginNumber(String beginNumber) {
            this.beginNumber = beginNumber;
        }

        public String getElecChequeNo() {
            return elecChequeNo;
        }

        public void setElecChequeNo(String elecChequeNo) {
            this.elecChequeNo = elecChequeNo;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public String getQueryNumber() {
            return queryNumber;
        }

        public void setQueryNumber(String queryNumber) {
            this.queryNumber = queryNumber;
        }

        public String getSerialNo() {
            return serialNo;
        }

        public void setSerialNo(String serialNo) {
            this.serialNo = serialNo;
        }

        public String getSingleOrBatchFlag() {
            return singleOrBatchFlag;
        }

        public void setSingleOrBatchFlag(String singleOrBatchFlag) {
            this.singleOrBatchFlag = singleOrBatchFlag;
        }

    }


}
