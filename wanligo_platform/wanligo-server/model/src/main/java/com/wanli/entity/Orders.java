package com.wanli.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_orders")
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "order_id", type = IdType.AUTO)
    private Long orderId;
    @TableField("account_id")
    private String accountId;
    @TableField("business_id")
    private Long businessId;
    @TableField("order_date")
    private String orderDate;
    @TableField("order_total")
    private BigDecimal orderTotal;
    @TableField("da_id")
    private Integer daId;
    /**
     * 订单状态（0：未支付； 1：已支付）
     */
    @TableField("order_state")
    private Integer orderState;

    @TableField("del_tag")
    private Integer delTag;
    @TableField(exist = false)
    private Business  business;
    @TableField(exist = false)
    private List<Orderdetailet> list;
}
