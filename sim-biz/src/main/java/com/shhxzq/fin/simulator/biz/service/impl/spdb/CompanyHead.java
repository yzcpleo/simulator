package com.shhxzq.fin.simulator.biz.service.impl.spdb;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by houjiagang on 16/7/18.
 */
public class CompanyHead {

    @XStreamAlias("transCode")
    private String transCode;

    @XStreamAlias("signFlag")
    private String signFlag;

    @XStreamAlias("masterID")
    private String masterID;

    @XStreamAlias("packetID")
    private String packetID;

    @XStreamAlias("timeStamp")
    private String timeStamp;

    public String getMasterID() {
        return masterID;
    }

    public void setMasterID(String masterID) {
        this.masterID = masterID;
    }

    public String getPacketID() {
        return packetID;
    }

    public void setPacketID(String packetID) {
        this.packetID = packetID;
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
