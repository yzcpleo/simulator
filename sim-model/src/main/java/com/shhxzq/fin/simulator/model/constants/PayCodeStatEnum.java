package com.shhxzq.fin.simulator.model.constants;

/**
 * 代扣错误码枚举
 *
 * @author kangyonggan
 * @since 16/5/10
 */
public enum PayCodeStatEnum {

    A("00", "1001", "Y", "交易成功"),//交易成功
    B("45", "2000", "I", "系统正在对数据处理"),//初始
    C("", "2000", "I", "系统正在对数据处理"),//初始
    D("45", "2045", "I", "未知结果"),//未知
    E("09", "2009", "I", "提交银行处理"),//超时未知
    F("E2", "20E2", "F", "数字签名或证书错"),//交易失败
    G("01", "2001", "F", "查开户方原因"),//账户/卡错误
    H("03", "2003", "F", "无效商户"),//交易失败
    I("05", "2005", "F", "未开通业务"),//交易失败
    J("06", "2006", "F", "系统处理失败"),//系统处理失败
    K("13", "2013", "F", "货币错误"),//交易失败
    L("14", "2014", "F", "无效卡号"),//账户/卡错误
    M("22", "2022", "F", "交易失败"),//交易失败
    N("30", "2030", "F", "报文内容检查错或者处理错"),//交易失败
    O("31", "2031", "F", "无路由或路由参数有误"),//交易失败
    P("41", "2041", "F", "账户/卡错误"),//已挂失折
    Q("51", "2051", "F", "余额不足"),//金额错误
    R("61", "2061", "F", "超出提款限额"),//金额错误
    S("94", "2094", "F", "重复业务"),//交易失败
    T("EC", "20EC", "F", "商户状态不合法"),//交易失败
    U("F3", "20F3", "F", "累计退货金额大于原交易金额"),//交易失败
    V("FF", "20FF", "F", "非白名单卡号"),//交易失败
    W("P9", "20P9", "F", "账户已冻结"),//交易失败
    X("PD", "20PD", "F", "账户未加办代收付标志"),//交易失败
    Y("PS", "20PS", "F", "户名不符"),//交易失败
    Z("PU", "20PU", "F", "订单号错误"),//交易失败
    A1("PZ", "20PZ", "F", "原交易信息不存在"),//交易失败
    B1("Q3", "20Q3", "F", "日期错误"),//交易失败
    C1("QB", "20QB", "F", "商户审核不通过"),//交易失败
    D1("QS", "20QS", "F", "系统忙，请稍后再提交"),//交易失败
    E1("ST", "20ST", "F", "已撤销"),//交易失败
    F1("T4", "20T4", "F", "未签约账户"),//交易失败
    G1("TY", "20TY", "F", "IP不通过"),//交易失败
    H1("EL", "20EL", "F", "交易失败"),//交易失败
    I1("01", "0001", "F", "系统处理失败"),//交易失败
    UNKNOW("UNKNOW", "UNKNOW", "I", "未知结果");

    private String respCode;//银行响应码
    private String transStat;//银行交易状态
    private String stat;//bs内部码
    private String msg;//银行返回信息

    PayCodeStatEnum(String respCode, String transStat, String stat, String msg) {
        this.respCode = respCode;
        this.transStat = transStat;
        this.stat = stat;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public String getRespCode() {
        return respCode;
    }

    public String getStat() {
        return stat;
    }

    public String getTransStat() {
        return transStat;
    }

    public static String findStatByRespCodeAndTransStat(String respCode, String transStat) {
        for (PayCodeStatEnum payCodeStatEnum : PayCodeStatEnum.values()) {
            if (payCodeStatEnum.getRespCode().equals(respCode) && payCodeStatEnum.getTransStat().equals(transStat)) {
                return payCodeStatEnum.getStat();
            }
        }
        return PayCodeStatEnum.UNKNOW.getStat();
    }
}
