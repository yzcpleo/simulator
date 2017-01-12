package com.shhxzq.fin.simulator.model.vo;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "sim_user")
public class User implements Serializable {

    /**
     * 主键, 自增
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 密码盐
     */
    private String salt;

    /**
     * 姓名
     */
    private String fullname;

    /**
     * 小头像
     */
    @Column(name = "small_avatar")
    private String smallAvatar;

    /**
     * 中头像
     */
    @Column(name = "medium_avatar")
    private String mediumAvatar;

    /**
     * 大头像
     */
    @Column(name = "large_avatar")
    private String largeAvatar;

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