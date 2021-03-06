package com.shhxzq.fin.simulator.biz.service.impl.boc;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by houjiagang on 16/8/3.
 */
@Setter
@Getter
@XStreamAlias("response")
public class Response {

    @XStreamAlias("head")
    private Head head;

    @XStreamAlias("body")
    private Body body;
}
