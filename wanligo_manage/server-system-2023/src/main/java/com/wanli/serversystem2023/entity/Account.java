package com.wanli.serversystem2023.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.wanli.serversystem2023.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author FJF
 * @since 2023-12-17
 */
@Data
@TableName("sys_account")
public class Account{

    private static final long serialVersionUID = 1L;

    @TableId(value = "account_id", type = IdType.AUTO)
    private String userId;

    @TableField("password")
    private String password;

    @TableField("account_name")
    private String userName;

    @TableField("account_sex")
    private Integer userSex;

    @TableField("account_img")
    private String userImg;

    @TableField("statu")
    private Integer statu;

    @TableField("del_tag")
    private Integer delTag;
}
