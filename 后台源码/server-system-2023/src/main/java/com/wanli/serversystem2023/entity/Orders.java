package com.wanli.serversystem2023.entity;

import java.math.BigDecimal;
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
 * @since 2023-12-14
 */
@Data
@TableName("sys_orders")
public class Orders{

    private static final long serialVersionUID = 1L;

    /**
     * 订单编号
     */
    @TableId(value = "order_id", type = IdType.AUTO)
    private Long orderId;

    /**
     * 用户编号
     */
    @TableField("account_id")
    private String userId;

    /**
     * 商家编号
     */
    @TableField("business_id")
    private Integer businessId;

    /**
     * 订购日期
     */
    @TableField("order_date")
    private String orderDate;

    /**
     * 订单总价
     */
    @TableField("order_total")
    private BigDecimal orderTotal;

    /**
     * 送货地址编号
     */
    @TableField("da_id")
    private Integer daId;

    /**
     * 订单状态（0：未支付； 1：已支付）
     */
    @TableField("order_state")
    private Integer orderState;

    @TableField("del_tag")
    private Integer delTag;
}
