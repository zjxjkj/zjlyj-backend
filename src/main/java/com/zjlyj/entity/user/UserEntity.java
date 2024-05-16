package com.zjlyj.entity.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zjlyj.core.base.BaseEntity;
import lombok.Data;

import java.util.Date;


@Data
@TableName("user")
public class UserEntity extends BaseEntity {

    /**
     * 主键
     */
    @TableId(type = IdType.INPUT)
    private Long id;

    /**
     * 名称
     */
    @TableField("name")
    private String name;

    /**
     * 账号
     */
    @TableField("account")
    private String account;

    /**
     * 密码
     */
    @TableField("password")
    private String password;

    /**
     * 手机号
     */
    @TableField("phone")
    private String phone;

    /**
     * 最后登录时间
     */
    @TableField("last_login_time")
    private Date lastLoginTime;

    /**
     * 最后登录IP
     */
    @TableField("last_login_ip")
    private String lastLoginIp;

}
