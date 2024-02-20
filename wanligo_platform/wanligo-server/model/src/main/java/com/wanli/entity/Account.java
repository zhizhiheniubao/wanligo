package com.wanli.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author Byterain
 * @since 2023-12-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_account")
public class Account implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "account_id", type = IdType.INPUT)
    private String accountId;

    @TableField("password")
    private String password;

    @TableField("account_name")
    private String accountName;

    @TableField("account_sex")
    private Integer accountSex;

    @TableField("account_img")
    private String accountImg;

    @TableField("statu")
    private Integer statu;

    @TableField("del_tag")
    private Integer delTag;


}
