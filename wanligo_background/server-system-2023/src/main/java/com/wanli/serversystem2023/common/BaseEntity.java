package com.wanli.serversystem2023.common;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;

public class BaseEntity {
    //实体类的父类，定义属性就是所有表的公共字段
    //id是主键，指定生成策略，采用mybatis-plus注解标识主键
    //type = IdType.AUTO 自动增长
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    private LocalDateTime created;
    private LocalDateTime updated;
    private Integer statu;
}
