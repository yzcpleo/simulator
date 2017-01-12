package com.shhxzq.fin.simulator.biz.service.impl.boc;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by houjiagang on 16/8/3.
 */
@Setter
@Getter
public class Head {

    @XStreamAlias("requestTime")
    private String requestTime;

    @XStreamAlias("requestSeq")
    private String requestSeq;

    @XStreamAlias("responseCode")
    private String responseCode;

    @XStreamAlias("responseInfo")
    private String responseInfo;

}
