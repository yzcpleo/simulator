package com.shhxzq.fin.simulator.biz.service.impl.ceb;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by houjiagang on 16/9/7.
 */
@Setter
@Getter
public class SystemHead {

    @XStreamAlias("Language")
    private String language;

    @XStreamAlias("Encodeing")
    private String encodeing;

    @XStreamAlias("Version")
    private String version;

    @XStreamAlias("ServiceName")
    private String serviceName;

    @XStreamAlias("CifNo")
    private String cifNo;

    @XStreamAlias("UserID")
    private String userID;

    @XStreamAlias("SyMacFlag")
    private String syMacFlag;

    @XStreamAlias("MAC")
    private String mac;

    @XStreamAlias("SyPinFlag")
    private String syPinFlag;

    @XStreamAlias("PinSeed")
    private String pinSeed;

    @XStreamAlias("LicenseId")
    private String licenseId;

    @XStreamAlias("Flag")
    private String flag;

    @XStreamAlias("Note")
    private String note;


}
