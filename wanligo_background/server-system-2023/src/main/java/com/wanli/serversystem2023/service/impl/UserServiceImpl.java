package com.wanli.serversystem2023.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wanli.serversystem2023.entity.Menu;
import com.wanli.serversystem2023.entity.Role;
import com.wanli.serversystem2023.entity.User;
import com.wanli.serversystem2023.entity.UserRole;
import com.wanli.serversystem2023.mapper.UserMapper;
import com.wanli.serversystem2023.service.MenuService;
import com.wanli.serversystem2023.service.RoleService;
import com.wanli.serversystem2023.service.UserRoleService;
import com.wanli.serversystem2023.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wanli.serversystem2023.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private MenuService menuService;
    @Autowired
    private UserRoleService userRoleService;

    @Override
    public User getUserByusername(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public String getUserAuthorityInfo(Long userId) {
        //定义权限字符串
        //权限字符串格式：用户所具备的角色，用户具体权限
        //ROLE_admin,ROLE_normal,sys:user:list,sys:user:save,sys:user:delete.....
        //权限字符串存储Redis中，键 grantedadmin   值=权限字符串
        String authorityString = "";

        //查询用户的详细信息：通过userId查询
        User user = this.getById(userId);
        String key = "granted" + user.getUsername();
        //判断 redis是否已经存在权限字符串。如果存在就直接从缓存中获取
        if(redisUtil.hasKey(key)){
            //如果存在就直接从缓存中获取     以String类型写入redis
            authorityString = (String) redisUtil.get(key);
        }else {
            //Redis中没有这个key，查询数据库，获取权限字符串，写入redis
            //查询当前userId这个用户具备哪些角色
            QueryWrapper role_qw = new QueryWrapper();
            role_qw.inSql("id","select role_id from sys_user_role where user_id=" + userId);
            List<Role> roles = roleService.list(role_qw);

            if(roles.size() > 0){
                String roleString = roles.stream().map(r -> "ROLE_" + r.getCode()).collect(Collectors.joining(","));
                authorityString = roleString+",";
            }

            List<Long> menusIds = userMapper.getNavMenuIds(userId);
            List<Menu> menus = menuService.listByIds(menusIds);

            if(menus.size() > 0){
                String menusString = menus.stream().map(r -> r.getPerms()).collect(Collectors.joining(","));
                authorityString = authorityString.concat(menusString);
            }
            redisUtil.set(key,authorityString);
        }
        return authorityString;
    }

    @Override
    public void clearUserAuthorityInfo(String username) {
        redisUtil.del("granted"+username);
    }

    @Override
    public void clearUserAuthorrityInfoByRoleId(Long roleId) {
        List<User> users = this.list(new QueryWrapper<User>().inSql("id", "select user_id from sys_user_role where role_id = " + roleId));
        users.forEach(u -> {
            this.clearUserAuthorityInfo(u.getUsername());
        });
    }

    @Override
    public void clearUserAuthorrityInfoByuserId(Long userId) {
        List<User> users = this.list(new QueryWrapper<User>().eq("id",userId));
        users.forEach(u -> {
            this.clearUserAuthorityInfo(u.getUsername());
        });
    }

    @Override
    public void clearUserAuthorityInfoByMenuId(Long menuId) {
        //查询出 目前和menuId相关的所有用户
        List<User> users = userMapper.listByMenuId(menuId);
        //根据查询出来的用户信息
        users.forEach(u->{
            this.clearUserAuthorityInfo(u.getUsername());
        });
    }
}
