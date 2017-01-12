package com.shhxzq.fin.simulator.biz.service.impl.icbc;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by houjiagang on 16/7/26.
 */
@Setter
@Getter
@XStreamAlias("CMS")
public class CMS {

    @XStreamAlias("eb")
    private EBody eBody;
}
