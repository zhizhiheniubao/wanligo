package com.wanli.serversystem2023.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wanli.serversystem2023.entity.Role;
import com.wanli.serversystem2023.entity.UserRole;
import com.wanli.serversystem2023.mapper.RoleMapper;
import com.wanli.serversystem2023.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wanli.serversystem2023.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author FJF
 * @since 2023-12-06
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Autowired
    UserRoleService userRoleService;
    @Override
    public List<Role> getUseRoleById(Long userId) {
        List<UserRole> userRoles = userRoleService.list(new QueryWrapper<UserRole>().eq("user_id", userId));
        if (userRoles.size()>0){
            List<Long> roleIds = userRoles.stream().map(ur -> ur.getRoleId()).collect(Collectors.toList());
            List<Role> roleList = this.listByIds(roleIds);
            return roleList;
        }
        return null;
    }
}
