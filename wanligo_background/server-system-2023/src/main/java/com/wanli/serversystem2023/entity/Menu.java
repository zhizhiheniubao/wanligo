package com.wanli.serversystem2023.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
 * @since 2023-12-06
 */
@Data
@TableName("sys_menu")
public class Menu extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 父菜单ID，一级菜单为0
     */
    @TableField("parent_id")
    private Long parentId;

    @TableField("name")
    private String name;

    /**
     * 菜单URL
     */
    @TableField("path")
    private String path;

    /**
     * 授权(多个用逗号分隔，如：user:list,user:create)
     */
    @TableField("perms")
    private String perms;

    @TableField("component")
    private String component;

    /**
     * 类型     0：目录   1：菜单   2：按钮
     */
    @TableField("type")
    private Integer type;

    /**
     * 菜单图标
     */
    @TableField("icon")
    private String icon;

    /**
     * 排序
     */
    @TableField("orderNum")
    private Integer ordernum;

    @TableField("created")
    private LocalDateTime created;

    @TableField("updated")
    private LocalDateTime updated;

    @TableField("statu")
    private Integer statu;

    //exist = false 表示该字段在数据库的表中不存在
    @TableField(exist = false)
    private List<Menu> children = new ArrayList<>();

}
