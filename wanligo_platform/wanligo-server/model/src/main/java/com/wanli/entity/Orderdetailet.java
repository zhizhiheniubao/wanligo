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
@TableName("sys_orderdetailet")
public class Orderdetailet implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 订单明细编号
     */
    @TableId(value = "od_id", type = IdType.AUTO)
    private Long odId;
    /**
     * 所属订单编号
     */
    @TableField("order_id")
    private Long orderId;
    /**
     * 食品编号
     */
    @TableField("food_id")
    private Integer foodId;
    /**
     * 数量
     */
    @TableField("quantity")
    private Integer quantity;
    @TableField("del_tag")
    private Integer delTag;
    @TableField(exist = false)
    private Food food;
}
