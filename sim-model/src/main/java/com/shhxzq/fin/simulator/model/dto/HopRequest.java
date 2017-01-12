package com.shhxzq.fin.simulator.model.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.serializer.SerializeFilter;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by houjiagang on 2016/12/21.
 */
@Setter
@Getter
@JSONType
public class HopRequest {
    @JSONField(name = "interfaceId")
    private String interfaceId;

    @JSONField(name = "companyNo")
    private String companyNo;

    @JSONField(name = "content")
    private String content;

    @JSONField(name = "signType")
    private String signType;

    @JSONField(name = "signature")
    private String signature;

    @JSONField(name = "subCompanyNo")
    private String subCompanyNo;
}
