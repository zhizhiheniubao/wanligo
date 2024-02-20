package com.wanli.serversystem2023.service;

import com.wanli.serversystem2023.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author FJF
 * @since 2023-12-06
 */
public interface RoleService extends IService<Role> {
    //根据用户查询所具备的角色
    public List<Role> getUseRoleById(Long userId);

}
