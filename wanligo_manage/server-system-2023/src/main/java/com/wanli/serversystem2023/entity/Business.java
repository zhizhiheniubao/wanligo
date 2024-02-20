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
 * @since 2023-12-17
 */
@Data
@TableName("sys_business")
public class Business{

    private static final long serialVersionUID = 1L;

    /**
     * 商家编号
     */
    @TableId(value = "business_id", type = IdType.AUTO)
    private Long businessId;

    /**
     * 商家名称
     */
    @TableField("business_name")
    private String businessName;

    /**
     * 商家地址
     */
    @TableField("business_address")
    private String businessAddress;

    /**
     * 商家介绍
     */
    @TableField("business_explain")
    private String businessExplain;

    /**
     * 商家图片（base64）
     */
    @TableField("business_img")
    private String businessImg;

    /**
     * 点餐分类
     */
    @TableField("category_id")
    private Integer categoryId;

    /**
     * 起送费
     */
    @TableField("star_price")
    private BigDecimal starPrice;

    @TableField("grade")
    private BigDecimal grade;

    /**
     * 配送费
     */
    @TableField("delivery_price")
    private BigDecimal deliveryPrice;

    /**
     * 备注
     */
    @TableField("remarks")
    private String remarks;

    @TableField("del_tag")
    private Integer delTag;
}
