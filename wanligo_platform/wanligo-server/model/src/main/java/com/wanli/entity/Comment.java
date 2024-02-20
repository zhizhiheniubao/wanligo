package com.wanli.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@TableName("sys_comment")
public class Comment {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    @TableField("comment")
    private String comment;
    @TableField("comment_img")
    private String commentImg;
    @TableField("grade")
    private BigDecimal grade;
    @TableField(exist = false)
    private List<String> commentImgList;
    @TableField("business_id")
    private Long businessId;
    @TableField("account_id")
    private Long accountId;
    @TableField(exist = false)
    private String accountName;
    @TableField(exist = false)
    private String accountImg;
    @TableField("pid")
    private Integer pid;
    @TableField(exist = false)
    private String pname;
    @TableField("origin_id")
    private Integer originId;
    @TableField(exist = false)
    private List<Comment> children;
    @TableField("create_time")
    private String createTime;
}
