package com.shhxzq.fin.simulator.model.constants;

import lombok.Getter;

import java.io.Serializable;

/**
 * 银行通道枚举
 *
 * @author kangyonggan
 * @since 2017/1/11
 */
public enum BankEnum implements Serializable {

    CP("cp", "中国银联"),
    ECT("ect", "上海证通"),
    GDNY("gndy", "广东南粤"),
    CMBCT0("cmbct0", "民生T+0"),
    SH2("sh2", "上海快捷"),
    SH3("sh3", "上海银企");

    @Getter
    private String bnkNm;

    @Getter
    private String bnkCo;

    private BankEnum(String bnkCo, String bnkNm) {
        this.bnkCo = bnkCo;
        this.bnkNm = bnkNm;
    }
}
