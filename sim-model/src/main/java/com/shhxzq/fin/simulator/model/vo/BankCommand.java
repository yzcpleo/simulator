package com.shhxzq.fin.simulator.model.vo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.Data;

@Table(name = "bank_command")
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
     * 出款方账号
     */
    @Column(name = "sndr_acco_no")
    private String sndrAccoNo;

    /**
     * 交易金额
     */
    private String amount;

    /**
     * 收款方账号
     */
    @Column(name = "rcvr_acco_no")
    private String rcvrAccoNo;

    /**
     * 工作日
     */
    @Column(name = "work_day")
    private String workDay;

    /**
     * 协议号
     */
    @Column(name = "protocol_no")
    private String protocolNo;

    /**
     * 备用1
     */
    @Column(name = "rcvr_resv1")
    private String rcvrResv1;

    /**
     * 备用2
     */
    @Column(name = "rcvr_resv2")
    private String rcvrResv2;

    /**
     * 备用3
     */
    @Column(name = "rcvr_resv3")
    private String rcvrResv3;

    /**
     * 备用4
     */
    @Column(name = "rcvr_resv4")
    private String rcvrResv4;

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