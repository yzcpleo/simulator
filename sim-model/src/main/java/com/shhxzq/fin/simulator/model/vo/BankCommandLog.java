package com.shhxzq.fin.simulator.model.vo;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "bank_command_log")
@Data
public class BankCommandLog implements Serializable {
    /**
     * 主键, 自增
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 商户流水号
     */
    @Column(name = "mer_serial_no")
    private String merSerialNo;

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

    /**
     * 请求报文
     */
    @Column(name = "req_xml")
    private String reqXml;

    /**
     * 响应报文
     */
    @Column(name = "res_xml")
    private String resXml;

    private static final long serialVersionUID = 1L;
}