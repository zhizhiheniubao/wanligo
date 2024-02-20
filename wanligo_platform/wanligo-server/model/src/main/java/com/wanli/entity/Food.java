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
@TableName("sys_food")
public class Food implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 食品编号
     */
    @TableId(value = "food_id", type = IdType.AUTO)
    private Integer foodId;

    /**
     * 食品名称
     */
    @TableField("food_name")
    private String foodName;

    /**
     * 食品介绍
     */
    @TableField("food_explain")
    private String foodExplain;

    /**
     * 食品图片
     */
    @TableField("food_img")
    private String foodImg;

    /**
     * 食品价格
     */
    @TableField("food_price")
    private BigDecimal foodPrice;

    /**
     * 所属商家编号
     */
    @TableField("business_id")
    private Long businessId;

    /**
     * 备注
     */
    @TableField("remarks")
    private String remarks;

    @TableField("del_tag")
    private Integer delTag;
    @TableField(exist = false)
    private Integer quantity;
}
