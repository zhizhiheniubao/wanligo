package com.wanli.serversystem2023.service;

import com.wanli.serversystem2023.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wanli.serversystem2023.entity.dto.MenuDto;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author FJF
 * @since 2023-12-06
 */
public interface MenuService extends IService<Menu> {
    //获得首页的系统惨淡：不同角色，不同权限，不同用户，菜单不同
    public List<MenuDto> getCurrentUserNav(String username);
    //菜单管理界面表格需要的层级菜单数据
    public List<Menu> tree();
}
