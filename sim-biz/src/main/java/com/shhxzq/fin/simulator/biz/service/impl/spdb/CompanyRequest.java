package com.shhxzq.fin.simulator.biz.service.impl.spdb;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by houjiagang on 16/7/18.
 */

@XStreamAlias("packet")
public class CompanyRequest {

    @XStreamAlias("head")
    private CompanyHead head;

    @XStreamAlias("body")
    private CompanyBody body;

    public CompanyBody getBody() {
        return body;
    }

    public void setBody(CompanyBody body) {
        this.body = body;
    }

    public CompanyHead getHead() {
        return head;
    }

    public void setHead(CompanyHead head) {
        this.head = head;
    }
}
