package com.wanli.serversystem2023.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
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
@TableName("sys_role")
public class Role extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("name")
    private String name;

    @TableField("code")
    private String code;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    @TableField("created")
    private LocalDateTime created;

    @TableField("updated")
    private LocalDateTime updated;

    @TableField("statu")
    private Integer statu;

    @TableField(exist = false)
    private List<Long> menuIds;
}
