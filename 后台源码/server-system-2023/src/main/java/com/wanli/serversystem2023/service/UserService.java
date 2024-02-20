package com.wanli.serversystem2023.service;

import com.wanli.serversystem2023.entity.Role;
import com.wanli.serversystem2023.entity.User;
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
public interface UserService extends IService<User> {
    //根据用户名查询
    public User getUserByusername(String username);

    //根据用户ID查询该用户的权限信息
    public String getUserAuthorityInfo(Long userId);

    //删除所有与该菜单关联的所有用户的权限信息
    public void clearUserAuthorityInfoByMenuId(Long menuId);

    //删除所有与用户名相关的权限信息
    public  void clearUserAuthorityInfo(String username);

    public void  clearUserAuthorrityInfoByRoleId(Long roleId);

    public void  clearUserAuthorrityInfoByuserId(Long userId);
}
