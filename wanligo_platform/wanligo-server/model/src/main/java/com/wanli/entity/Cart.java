package com.wanli.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_cart")
public class Cart implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(value = "cart_id", type = IdType.AUTO)
    private Integer cartId;
    @TableField("food_id")
    private Integer foodId;
    @TableField("business_id")
    private Long businessId;
    @TableField("account_id")
    private String accountId;
    @TableField("quantity")
    private Integer quantity;

    @TableField("del_tag")
    private Integer delTag;

    @TableField(exist = false)
    private Food food;
    @TableField(exist =  false)
    private Business business;
}
