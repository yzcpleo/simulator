package com.shhxzq.fin.simulator.model.vo;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "sim_bank_command")
@Data
public class BankCommand implements Serializable {
    /**
     * 主键, 自增
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 银行代码
     */
    @Column(name = "bnk_co")
    private String bnkCo;

    /**
     * 银行名称
     */
    @Column(name = "bnk_nm")
    private String bnkNm;

    /**
     * 商户流水号
     */
    @Column(name = "mer_serial_no")
    private String merSerialNo;

    /**
     * 银行流水号
     */
    @Column(name = "bnk_serial_no")
    private String bnkSerialNo;

    /**
     * 交易代码
     */
    @Column(name = "tran_co")
    private String tranCo;

    /**
     * 交易名称
     */
    @Column(name = "tran_nm")
    private String tranNm;

    /**
     * 商户交易日期
     */
    @Column(name = "mer_date")
    private String merDate;

    /**
     * 商户交易时间
     */
    @Column(name = "mer_time")
    private String merTime;

    /**
     * 币种
     */
    @Column(name = "cur_co")
    private String curCo;

    /**
     * 付款方账号
     */
    @Column(name = "sndr_acco_no")
    private String sndrAccoNo;

    /**
     * 付款方账号名称
     */
    @Column(name = "sndr_acco_nm")
    private String sndrAccoNm;

    /**
     * 付款方证件类型
     */
    @Column(name = "sndr_id_tp")
    private String sndrIdTp;

    /**
     * 付款方证件号码
     */
    @Column(name = "sndr_id_no")
    private String sndrIdNo;

    /**
     * 付款方保留字段1
     */
    @Column(name = "sndr_resv1")
    private String sndrResv1;

    /**
     * 付款方保留字段2
     */
    @Column(name = "sndr_resv2")
    private String sndrResv2;

    /**
     * 交易金额
     */
    private String amount;

    /**
     * 手续费
     */
    @Column(name = "fee_amt")
    private String feeAmt;

    /**
     * 转账用途
     */
    @Column(name = "tran_purpose")
    private String tranPurpose;

    /**
     * 转账附言
     */
    @Column(name = "tran_remark")
    private String tranRemark;

    /**
     * 收款方账号
     */
    @Column(name = "rcvr_acco_no")
    private String rcvrAccoNo;

    /**
     * 收款方账号名称
     */
    @Column(name = "rcvr_acct_nm")
    private String rcvrAcctNm;

    /**
     * 收款方证件类型
     */
    @Column(name = "rcvr_id_tp")
    private String rcvrIdTp;

    /**
     * 收款方证件号码
     */
    @Column(name = "rcvr_id_no")
    private String rcvrIdNo;

    /**
     * 收款方保留字段1
     */
    @Column(name = "rcvr_resv1")
    private String rcvrResv1;

    /**
     * 收款方保留字段2
     */
    @Column(name = "rcvr_resv2")
    private String rcvrResv2;

    /**
     * 工作日(对账时使用)
     */
    @Column(name = "work_day")
    private String workDay;

    /**
     * 协议号
     */
    @Column(name = "protocol_no")
    private String protocolNo;

    /**
     * 错误码
     */
    @Column(name = "resp_co")
    private String respCo;

    /**
     * 错误描述
     */
    @Column(name = "resp_msg")
    private String respMsg;

    /**
     * 锁状态:{N:未锁,T:正在交易}
     */
    @Column(name = "lock_st")
    private String lockSt;

    /**
     * 交易状态:{Y:成功,F:失败,I:处理中}
     */
    @Column(name = "tran_st")
    private String tranSt;

    /**
     * 逻辑删除:{0:未删除, 1:已删除}
     */
    @Column(name = "is_deleted")
    private Byte isDeleted;

    /**
     * 创建时间
     */
    @Column(name = "created_time")
    private Date createdTime;

    /**
     * 更新时间
     */
    @Column(name = "updated_time")
    private Date updatedTime;

    private static final long serialVersionUID = 1L;
}