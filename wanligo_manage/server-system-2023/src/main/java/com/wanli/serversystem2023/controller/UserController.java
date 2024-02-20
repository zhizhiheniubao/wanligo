package com.wanli.serversystem2023.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wanli.serversystem2023.common.Const;
import com.wanli.serversystem2023.common.Result;
import com.wanli.serversystem2023.entity.Role;
import com.wanli.serversystem2023.entity.RoleMenu;
import com.wanli.serversystem2023.entity.User;
import com.wanli.serversystem2023.entity.UserRole;
import com.wanli.serversystem2023.service.RoleService;
import com.wanli.serversystem2023.service.UserRoleService;
import com.wanli.serversystem2023.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.wanli.serversystem2023.common.BaseController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author FJF
 * @since 2023-12-06
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserRoleService userRoleService;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    //更新用户密码
    @GetMapping("/updatePassword")
    public Result updatePassword(Long id,String password,String currentPass){
        User user = userService.getById(id);
        if (bCryptPasswordEncoder.matches(currentPass,user.getPassword())){
            user.setPassword(passwordEncoder.encode(password));
            user.setUpdated(LocalDateTime.now());
            userService.updateById(user);
            return Result.success("密码更新成功  请重新登录");
        }
        return Result.fail("原密码输入错误");
    }

    //保存用户分配角色
    @Transactional
    @PostMapping("/role/{id}")
    public Result role(@PathVariable Long id,@RequestBody Long[] roleIds) {
        //封装关系表: sys_user_role
        List<UserRole> userRoleList = new ArrayList<>();
        Arrays.stream(roleIds).forEach(roleId -> {
            UserRole ur = new UserRole();
            ur.setUserId(id);
            ur.setRoleId(roleId);
            userRoleList.add(ur);
        });
        //先清除关联表中当前用户所有的 关联数据
        userRoleService.remove(new QueryWrapper<UserRole>().eq("user_id", id));
        userRoleService.saveBatch(userRoleList); //将用户和角色的关系数据 保存到关联表
        //用户角色改变，权限也发生改变，需要清除Redis中缓存
        User user = userService.getById(id);
        userService.clearUserAuthorityInfo(user.getUsername());
        return Result.success("用户角色分配成功");
    }

    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('sys:user:delete')")
    public Result delete(@RequestBody Long[] userIds){
        Arrays.asList(userIds).forEach(userId -> {
            userService.clearUserAuthorrityInfoByuserId(userId);
        });
        userService.removeByIds(Arrays.asList(userIds));
        userRoleService.remove(new QueryWrapper<UserRole>().in("user_id",userIds));
        return Result.success("用户删除成功");
    }

    @GetMapping("/info/{id}")
    public Result info(@PathVariable("id") Long id){
        User user = userService.getById(id);
        List<Role> roles = roleService.getUseRoleById(user.getId());
        user.setRoles(roles);
        return Result.success(user);
    }

    @PostMapping("/update")
    public Result update(@RequestBody User user){
        user.setUpdated(LocalDateTime.now());
        userService.updateById(user);
        return Result.success("用户更新成功");
    }

    @PostMapping("/save")
    public Result save(@RequestBody User user){
        user.setCreated(LocalDateTime.now());
        user.setStatu(Const.STATUS_ON);
        user.setPassword(passwordEncoder.encode(Const.RESET_PASSWORD));
        userService.save(user);
        return Result.success("用户保存成功");
    }

    //重置用户密码
    @PreAuthorize("hasAuthority('sys:user:repass')")
    @PostMapping("/repass/{id}")
    public Result repass(@PathVariable("id") Long id){
        User user = userService.getById(id);
        user.setPassword(passwordEncoder.encode(Const.RESET_PASSWORD));
        user.setUpdated(LocalDateTime.now());
        userService.updateById(user);
        return Result.success("密码重置成功");
    }

    @GetMapping("/list")
    public Result list(String name){
        Page<User> users = userService.page(getPage(),new QueryWrapper<User>().like(StrUtil.isNotBlank(name),"username",name));
        users.getRecords().stream().forEach( u ->{
            List<Role> roles = roleService.getUseRoleById(u.getId());
            u.setRoles(roles);
        });
        return Result.success(users);
    }
}
