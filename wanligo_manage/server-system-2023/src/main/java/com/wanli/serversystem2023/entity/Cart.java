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
@TableName("sys_cart")
public class Cart{

    private static final long serialVersionUID = 1L;

    /**
     * 无意义编号
     */
    @TableId(value = "cart_id", type = IdType.AUTO)
    private Integer cartId;

    /**
     * 食品编号
     */
    @TableField("food_id")
    private Integer foodId;

    /**
     * 所属商家编号
     */
    @TableField("business_id")
    private Integer businessId;

    /**
     * 所属用户编号
     */
    @TableField("account_id")
    private String userId;

    /**
     * 同一类型食品的购买数量
     */
    @TableField("quantity")
    private Integer quantity;

    @TableField("del_tag")
    private Integer delTag;
}
