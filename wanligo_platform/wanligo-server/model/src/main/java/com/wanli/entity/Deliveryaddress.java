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
 * @since 2023-12-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_deliveryaddress")
public class Deliveryaddress implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 送货地址编号
     */
    @TableId(value = "da_id", type = IdType.AUTO)
    private Integer daId;

    /**
     * 联系人姓名
     */
    @TableField("contact_name")
    private String contactName;

    /**
     * 联系人性别
     */
    @TableField("contact_sex")
    private Integer contactSex;

    /**
     * 联系人电话
     */
    @TableField("contact_tel")
    private String contactTel;

    /**
     * 送货地址
     */
    @TableField("address")
    private String address;

    /**
     * 所属用户编号
     */
    @TableField("account_id")
    private String accountId;


}
