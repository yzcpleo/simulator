package com.shhxzq.fin.simulator.biz.service.impl.spdb;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by houjiagang on 16/7/15.
 */
@XStreamAlias("packet")
public class Request {

    @XStreamAlias("transName")
    private String transName;

    @XStreamAlias("Plain")
    private String plain;

    @XStreamAlias("Signature")
    private String signature;

    public String getPlain() {
        return plain;
    }

    public void setPlain(String plain) {
        this.plain = plain;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getTransName() {
        return transName;
    }

    public void setTransName(String transName) {
        this.transName = transName;
    }
}
