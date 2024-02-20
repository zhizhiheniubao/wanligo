package com.wanli.serversystem2023.service.impl;

import com.wanli.serversystem2023.entity.Menu;
import com.wanli.serversystem2023.entity.User;
import com.wanli.serversystem2023.entity.dto.MenuDto;
import com.wanli.serversystem2023.mapper.MenuMapper;
import com.wanli.serversystem2023.mapper.UserMapper;
import com.wanli.serversystem2023.service.MenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wanli.serversystem2023.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author FJF
 * @since 2023-12-06
 */
@Service

public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {
    @Autowired
    UserService userService;
    @Autowired
    UserMapper userMapper;

    @Override
    public List<MenuDto> getCurrentUserNav(String username) {
        //根据用户名username查询用户的信息
        User user = userService.getUserByusername(username);
        //根据用户Id查询用户所具备的菜单id
        List<Long> navMenuIds = userMapper.getNavMenuIds(user.getId());

        List<Menu> menus = this.listByIds(navMenuIds);

        List<Menu> finalMenu = buildTreeMenu(menus);
        List<MenuDto> menuDtos = convent(finalMenu);
        return menuDtos;
    }

    @Override
    public List<Menu> tree() {
        List<Menu> menus = this.list();
        List<Menu> treeMenu = this.buildTreeMenu(menus);
        return treeMenu;
    }

    //将集合中Menu对象转换为MenuDto
    private List<MenuDto> convent(List<Menu> menus){
        List<MenuDto> menuDtoList = new ArrayList<>();
        menus.forEach(m -> {
            MenuDto dto = new MenuDto();
            dto.setId(m.getId());
            dto.setName(m.getPerms());
            dto.setTitle(m.getName());
            dto.setIcon(m.getIcon());
            dto.setPath(m.getPath());
            dto.setComponent(m.getComponent());
            if(m.getChildren().size() > 0){
                dto.setChildren(convent(m.getChildren()));
            };
            menuDtoList.add(dto);
        });
        return menuDtoList;
    }

    //将查询出来的惨淡数据 变为具有层级关系的 菜单数据TreeMenu
    private List<Menu> buildTreeMenu(List<Menu> menus){
        //最终菜单，已经通过循环便利查找所有层级关系后的菜单数据
        List<Menu> finalMenu = new ArrayList<>();

        for(Menu m : menus){
            //寻找子菜单
            for(Menu e : menus){
                if(e.getParentId() == m.getId()){
                    //当前虚幻的e就是m子菜单
                    m.getChildren().add(e);
                }
            }
            if(m.getParentId() == 0L){
                finalMenu.add(m);
            }
        }
        return finalMenu;
    }
}
