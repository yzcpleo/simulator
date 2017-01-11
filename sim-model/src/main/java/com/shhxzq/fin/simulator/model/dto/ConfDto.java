package com.shhxzq.fin.simulator.model.dto;

import com.shhxzq.fin.simulator.model.BaseObject;
import lombok.Data;

/**
 * @author kangyonggan
 * @since 2017/1/11
 */
@Data
public class ConfDto extends BaseObject {

    /**
     * 发短信
     */
    private String sms;

    /**
     * 鉴权
     */
    private String verify;

    /**
     * 签约
     */
    private String sign;

    /**
     * 申购
     */
    private String pay;

    /**
     * 赎回
     */
    private String redeem;

    /**
     * 跨行赎回
     */
    private String interRedeem;

}
