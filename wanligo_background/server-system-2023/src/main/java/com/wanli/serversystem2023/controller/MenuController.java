package com.wanli.serversystem2023.controller;


import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wanli.serversystem2023.common.Result;
import com.wanli.serversystem2023.entity.Menu;
import com.wanli.serversystem2023.entity.RoleMenu;
import com.wanli.serversystem2023.entity.User;
import com.wanli.serversystem2023.entity.dto.MenuDto;
import com.wanli.serversystem2023.service.MenuService;
import com.wanli.serversystem2023.service.RoleMenuService;
import com.wanli.serversystem2023.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import com.wanli.serversystem2023.common.BaseController;

import java.security.Principal;
import java.time.LocalDateTime;
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
@RequestMapping("/menu")
public class MenuController extends BaseController {
    @Autowired
    MenuService menuService;
    @Autowired
    UserService userService;
    @Autowired
    RoleMenuService roleMenuService;

    //获取菜单信息
    @GetMapping("/info/{id}")
    public Result info(@PathVariable Integer id){
        Menu menu = menuService.getById(id);
        return Result.success(menu);
    }

    //更新菜单的方法
    @PostMapping("/update")
    public Result update(@RequestBody Menu menu){
        menu.setUpdated(LocalDateTime.now());
        menuService.updateById(menu);
        return Result.success("菜单更新成功");
    }

    //新建菜单的方法
    @PostMapping("/save")
    public Result save(@RequestBody Menu menu){
        menu.setCreated(LocalDateTime.now());
        menuService.save(menu);
        return Result.success("菜单保存成功");
    }

    //根据id删除菜单
    @GetMapping("/delete/{id}")
    @Transactional
    public Result delete(@PathVariable("id") Long id){
        int count = menuService.count(new QueryWrapper<Menu>().eq("parent_id",id));
        if(count>0){
            return Result.fail("请先删除该菜单下面的子菜单");
        }else {
            userService.clearUserAuthorityInfoByMenuId(id);
            menuService.removeById(id);
            roleMenuService.remove(new QueryWrapper<RoleMenu>().eq("menu_id",id));
            return Result.success("菜单删除成功");
        }
    }

    //菜单管理界面管理表格：获得菜单数据
    @GetMapping("/list")
    public Result list(){
        return Result.success(menuService.tree());
    }

    //SpringSecurity如果需要再Controller的方法中获得登录用户名，可以使用Principal
    @GetMapping("/nav")
    public Result nav(Principal principal){
        //查询菜单数据需要当前登录用户名
        String username = principal.getName();
        List<MenuDto> currentUserNav = menuService.getCurrentUserNav(username);

        //构建返回Vue权限信息
        User user = userService.getUserByusername(username);
        //查询当前用户的权限字符串
        String userAuthorityInfo = userService.getUserAuthorityInfo(user.getId());
        //将权限字符串按照逗号分割，转换为数组
        String[] authoritys = StringUtils.tokenizeToStringArray(userAuthorityInfo, ",");

        return Result.success(MapUtil.builder().put("nav",currentUserNav).put("authoritys",authoritys).map());
    }

}

