package com.shhxzq.fin.simulator.model.vo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "bank_resp")
public class BankResp implements Serializable {
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