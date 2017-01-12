package com.shhxzq.fin.simulator.biz.service.impl.ceb;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by houjiagang on 16/9/6.
 */
@Setter
@Getter
@XStreamAlias("MessageSuit")
public class MessageSuit {

    @XStreamAlias("Message")
    private Message message;

    @Setter
    @Getter
    public static class Message{

        @XStreamAsAttribute()
        @XStreamAlias("id")
        private String id;

        @XStreamAlias("Plain")
        private Plain plain;

        @XStreamAlias("ds:Signature")
        private String signature;


    }




}
