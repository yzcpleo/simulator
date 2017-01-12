package com.shhxzq.fin.simulator.model.vo;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "sim_bank_tran")
@Data
public class BankTran implements Serializable {
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
     * 对账文件模板
     */
    @Column(name = "dz_temp")
    private String dzTemp;

    /**
     * 是否生成对账文件:{0:不生成, 1:生成}
     */
    @Column(name = "is_gen_dz")
    private Byte isGenDz;

    /**
     * 是否推送对账文件:{0:不推送, 1:推送}
     */
    @Column(name = "is_push_dz")
    private Byte isPushDz;

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