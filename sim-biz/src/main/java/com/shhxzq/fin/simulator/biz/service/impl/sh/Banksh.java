package com.shhxzq.fin.simulator.biz.service.impl.sh;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by houjiagang on 2016/11/16.
 */
@Setter
@Getter
@XStreamAlias("Banksh")
public class Banksh {

    @XStreamAlias("Message")
    private Message message;

}
