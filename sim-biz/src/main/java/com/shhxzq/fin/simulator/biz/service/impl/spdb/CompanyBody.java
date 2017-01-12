package com.shhxzq.fin.simulator.biz.service.impl.spdb;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by houjiagang on 16/7/18.
 */
public class CompanyBody {

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


    public static class Body {


        @XStreamAlias("elecChequeNo")
        private String elecChequeNo;

        @XStreamAlias("authMasterID")
        private String authMasterID;

        @XStreamAlias("acctNo")
        private String acctNo;

        @XStreamAlias("acctName")
        private String acctName;

        @XStreamAlias("bespeakDate")
        private String bespeakDate;

        @XStreamAlias("payeeAcctNo")
        private String payeeAcctNo;

        @XStreamAlias("payeeName")
        private String payeeName;

        @XStreamAlias("payeeType")
        private String payeeType;

        @XStreamAlias("payeeBankName")
        private String payeeBankName;

        @XStreamAlias("payeeAddress")
        private String payeeAddress;

        @XStreamAlias("amount")
        private String amount;

        @XStreamAlias("sysFlag")
        private String sysFlag;

        @XStreamAlias("remitLocation")
        private String remitLocation;

        @XStreamAlias("note")
        private String note;

        @XStreamAlias("payeeBankSelectFlag")
        private String payeeBankSelectFlag;

        @XStreamAlias("payeeBankNo")
        private String payeeBankNo;

        @XStreamAlias("PayeeBankNo")
        private String payeeBankNo1;

        @XStreamAlias("beginDate")
        private String beginDate;

        @XStreamAlias("endDate")
        private String endDate;

        @XStreamAlias("queryPage")
        private String queryPage;

        @XStreamAlias("fileName")
        private String fileName;


        public String getBeginDate() {
            return beginDate;
        }

        public void setBeginDate(String beginDate) {
            this.beginDate = beginDate;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public String getQueryPage() {
            return queryPage;
        }

        public void setQueryPage(String queryPage) {
            this.queryPage = queryPage;
        }

        public String getPayeeBankNo1() {
            return payeeBankNo1;
        }

        public void setPayeeBankNo1(String payeeBankNo1) {
            this.payeeBankNo1 = payeeBankNo1;
        }

        @XStreamAlias("electronNumber")
        private String electronNumber;

        public String getElectronNumber() {
            return electronNumber;
        }

        public void setElectronNumber(String electronNumber) {
            this.electronNumber = electronNumber;
        }

        public String getAcctName() {
            return acctName;
        }

        public void setAcctName(String acctName) {
            this.acctName = acctName;
        }

        public String getAcctNo() {
            return acctNo;
        }

        public void setAcctNo(String acctNo) {
            this.acctNo = acctNo;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getAuthMasterID() {
            return authMasterID;
        }

        public void setAuthMasterID(String authMasterID) {
            this.authMasterID = authMasterID;
        }

        public String getBespeakDate() {
            return bespeakDate;
        }

        public void setBespeakDate(String bespeakDate) {
            this.bespeakDate = bespeakDate;
        }

        public String getElecChequeNo() {
            return elecChequeNo;
        }

        public void setElecChequeNo(String elecChequeNo) {
            this.elecChequeNo = elecChequeNo;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public String getPayeeAcctNo() {
            return payeeAcctNo;
        }

        public void setPayeeAcctNo(String payeeAcctNo) {
            this.payeeAcctNo = payeeAcctNo;
        }

        public String getPayeeAddress() {
            return payeeAddress;
        }

        public void setPayeeAddress(String payeeAddress) {
            this.payeeAddress = payeeAddress;
        }

        public String getPayeeBankName() {
            return payeeBankName;
        }

        public void setPayeeBankName(String payeeBankName) {
            this.payeeBankName = payeeBankName;
        }

        public String getPayeeBankNo() {
            return payeeBankNo;
        }

        public void setPayeeBankNo(String payeeBankNo) {
            this.payeeBankNo = payeeBankNo;
        }

        public String getPayeeBankSelectFlag() {
            return payeeBankSelectFlag;
        }

        public void setPayeeBankSelectFlag(String payeeBankSelectFlag) {
            this.payeeBankSelectFlag = payeeBankSelectFlag;
        }

        public String getPayeeName() {
            return payeeName;
        }

        public void setPayeeName(String payeeName) {
            this.payeeName = payeeName;
        }

        public String getPayeeType() {
            return payeeType;
        }

        public void setPayeeType(String payeeType) {
            this.payeeType = payeeType;
        }

        public String getRemitLocation() {
            return remitLocation;
        }

        public void setRemitLocation(String remitLocation) {
            this.remitLocation = remitLocation;
        }

        public String getSysFlag() {
            return sysFlag;
        }

        public void setSysFlag(String sysFlag) {
            this.sysFlag = sysFlag;
        }
    }





}
