package com.wanli.serversystem2023.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
@TableName("sys_food")
public class Food {

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

}
