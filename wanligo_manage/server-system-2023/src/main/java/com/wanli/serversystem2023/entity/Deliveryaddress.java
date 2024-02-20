package com.wanli.serversystem2023.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.wanli.serversystem2023.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author FJF
 * @since 2023-12-15
 */
@Data
@TableName("sys_deliveryaddress")
public class Deliveryaddress{

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

    @TableField(exist = false)
    private List<Account> accounts;
}
