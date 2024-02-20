package com.wanli.serversystem2023.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wanli.serversystem2023.common.Result;
import com.wanli.serversystem2023.entity.Role;
import com.wanli.serversystem2023.entity.RoleMenu;
import com.wanli.serversystem2023.entity.UserRole;
import com.wanli.serversystem2023.service.RoleMenuService;
import com.wanli.serversystem2023.service.RoleService;
import com.wanli.serversystem2023.service.UserRoleService;
import com.wanli.serversystem2023.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.wanli.serversystem2023.common.BaseController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author FJF
 * @since 2023-12-06
 */
@RestController
@RequestMapping("/role")
public class RoleController extends BaseController {
    @Autowired
    RoleService roleService;
    @Autowired
    RoleMenuService roleMenuService;
    @Autowired
    UserRoleService userRoleService;
    @Autowired
    UserService userService;

    @GetMapping("/tree")
    public Result list(){
        List<Role> list = roleService.list();
        return Result.success(list);
    }


    @PostMapping("/perm/{roleId}")
    public Result perm(@PathVariable("roleId") Long roleId,@RequestBody Long[] menuIds){
        List<RoleMenu> roleMenus = new ArrayList<>();
        Arrays.stream(menuIds).forEach(menuId -> {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setMenuId(menuId);
            roleMenu.setRoleId(roleId);
            roleMenus.add(roleMenu);
        });

        roleMenuService.remove(new QueryWrapper<RoleMenu>().eq("role_id",roleId));
        roleMenuService.saveBatch(roleMenus);
        userService.clearUserAuthorrityInfoByRoleId(roleId);
        return Result.success("角色权限信息保存成功");
    }

    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('sys:role:delete')")
    public Result delete(@RequestBody Long[] ids){
        Arrays.asList(ids).forEach(id ->{
            userService.clearUserAuthorrityInfoByRoleId(id);
        });
        roleService.removeByIds(Arrays.asList(ids));
        roleMenuService.remove(new QueryWrapper<RoleMenu>().in("role_id",ids));
        userRoleService.remove(new QueryWrapper<UserRole>().in("role_id",ids));
        return Result.success("角色删除成功");
    }

    @GetMapping("/info/{id}")
    public Result info(@PathVariable("id") Long id){
        Role role = roleService.getById(id);

        List<RoleMenu> roleMenus = roleMenuService.list(new QueryWrapper<RoleMenu>().eq("role_id", id));
        List<Long> menuIds = roleMenus.stream().map(rm -> rm.getMenuId()).collect(Collectors.toList());
        role.setMenuIds(menuIds);
        return Result.success(role);
    }

    @PreAuthorize("hasAnyAuthority('sys:role:save')")
    @PostMapping("/save")
    public Result save(@RequestBody Role role){
        role.setCreated(LocalDateTime.now());
        roleService.save(role);
        return Result.success("角色保存完成");
    }

    @PreAuthorize("hasAnyAuthority('sys:role:update')")
    @PostMapping("/update")
    public Result update(@RequestBody Role role){
        role.setUpdated(LocalDateTime.now());
        roleService.updateById(role);
        return Result.success("角色信息更新完成");
    }

    //菜单管理界面管理表格：获得菜单数据
    @GetMapping("/list")
    public Result list(String name){
        //分页参数：显示页码，一页记录数
        Page<Role> roles = roleService.page(getPage(),new QueryWrapper<Role>().like(StrUtil.isNotBlank(name),"name",name));
        return Result.success(roles);
    }
}
