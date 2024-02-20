package com.wanli.serversystem2023;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wanli.serversystem2023.common.Const;
import com.wanli.serversystem2023.service.UserService;
import com.wanli.serversystem2023.util.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@SpringBootTest
class ServerSystem2023ApplicationTests {
//    @Autowired
//    private BCryptPasswordEncoder bCryptPasswordEncoder;

//    @Autowired
//    private UserService userService;
//
//    @Test
//    void testAuth(){
//        String userAuthorityInfo = userService.getUserAuthorityInfo(1L);
//        System.out.println(userAuthorityInfo);
//    }
//
//    @Test
//    void testPassword(){
//        String pwd = "123";
//        String encode = bCryptPasswordEncoder.encode(pwd);
//        System.out.println("加密后:"+encode);
//        String abc123 = "123";
//        boolean matches = bCryptPasswordEncoder.matches(abc123, encode);
//        System.out.println(matches);
//    }


//    @Autowired
//    private SysUserMapper sysUserMapper;
//    @Autowired
//    private SysMenuMapper sysMenuMapper;

//    @Test
//    void contextLoads() {
//        List<SysUser> sysUsers = sysUserMapper.selectList(null);
//        System.out.println(sysUsers);
//    }
//
//    @Test
//    void testInsert(){
//        SysUser sysUser = sysUserMapper.selectById(43);
//        sysUser.setUsername("WWWWWW");
//        sysUser.setPassword("123");
//        sysUser.setId(null);
//        int insert = sysUserMapper.insert(sysUser);
//        System.out.println(insert);
//    }
//
//    @Test
//    void testUpdate(){
//        SysUser sysUser = sysUserMapper.selectById(44);
//        sysUser.setUsername("CCCCCC");
//        sysUserMapper.updateById(sysUser);
//    }
//
//    @Test
//    void testSelectWrapper(){
//        //查询菜单 type=2 记录数
//        QueryWrapper queryWrapper = new QueryWrapper();
//        queryWrapper.eq("type",2);
//        queryWrapper.ne("statu",0);
//        queryWrapper.like("name","用户");
//        List list = sysMenuMapper.selectList(queryWrapper);
//        System.out.println(list);
//    }


}
