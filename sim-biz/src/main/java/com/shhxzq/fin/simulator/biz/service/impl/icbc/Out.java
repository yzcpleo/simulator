package com.shhxzq.fin.simulator.biz.service.impl.icbc;


import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by houjiagang on 16/7/26.
 */
@Setter
@Getter
public class Out extends Transaction {

    @XStreamAlias("VfRetCode")
    private String vfRetCode;

    @XStreamAlias("VfRetMsg")
    private String vfRetMsg;

    @XStreamAlias("DelTime")
    private String delTime;

    @XStreamAlias("BusType")
    private String busType;

    private List<Query> querys;


}
