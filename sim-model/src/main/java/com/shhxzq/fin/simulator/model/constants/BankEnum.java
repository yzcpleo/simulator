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
    BOC("boc", "中国银行"),
    SPDB("spdb", "上海浦发"),
    CEB("ceb", "光大银行"),
    CCB("ccb", "建设银行"),
    CMBC("cmbc", "民生超网"),
    CMBC2("cmbc2", "民生快捷"),
    ICBC("icbc", "工商银行"),
    CMB("cmb", "招商银行"),
    PAB("pab", "平安银行"),
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
