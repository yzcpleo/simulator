package com.shhxzq.fin.simulator.biz.service.impl.ccb;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by houjiagang on 16/7/21.
 */
@Setter
@Getter
@XStreamAlias("Tran")
public class Tran {

    @XStreamAlias("Header")
    private Header header;

    @XStreamAlias("Body")
    private Body body;
}
