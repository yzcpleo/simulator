package com.shhxzq.fin.simulator.biz.service.impl.sh;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by houjiagang on 2016/11/15.
 */
@Setter
@Getter
@XStreamAlias("Message")
public class Message {

    @XStreamAsAttribute()
    @XStreamAlias("id")
    private String id;

    @XStreamAlias("CSReq")
    private Request request;

    @XStreamAlias("CSRes")
    private Response response;

    @XStreamAlias("Signature")
    private String signature;
}
