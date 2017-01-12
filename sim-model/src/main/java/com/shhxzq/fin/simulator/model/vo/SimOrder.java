package com.shhxzq.fin.simulator.model.vo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.Data;

@Table(name = "sim_order")
@Data
public class SimOrder implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String amount;

    @Column(name = "bank_card_no")
    private String bankCardNo;

    @Column(name = "cert_no")
    private String certNo;

    @Column(name = "cert_type")
    private String certType;

    @Column(name = "charge_type")
    private String chargeType;

    @Column(name = "cust_type")
    private String custType;

    private String mobile;

    private String name;

    @Column(name = "payment_type")
    private String paymentType;

    @Column(name = "prod_id")
    private String prodId;

    private String remark;

    @Column(name = "risk_level")
    private String riskLevel;

    @Column(name = "serial_no")
    private String serialNo;

    private String share;

    private String apkind;

    @Column(name = "trade_acct")
    private String tradeAcct;

    @Column(name = "order_type")
    private String orderType;

    @Column(name = "company_no")
    private String companyNo;

    @Column(name = "sub_company_no")
    private String subCompanyNo;

    @Column(name = "partner_cust_no")
    private String partnerCustNo;

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