package com.shhxzq.fin.simulator.biz.service.impl.ceb;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;
import lombok.Setter;


/**
 * Created by houjiagang on 16/9/7.
 */
@Setter
@Getter
public class TransHead {

    @XStreamAlias("TransCode")
    private String transCode;

    @XStreamAlias("BatchID")
    private String batchID;

    @XStreamAlias("JnlDate")
    private String jnlDate;

    @XStreamAlias("JnlTime")
    private String jnlTime;

    @XStreamAlias("response1")
    private String response1;

    @XStreamAlias("response2")
    private String response2;

    @XStreamAlias("response3")
    private String response3;

}
