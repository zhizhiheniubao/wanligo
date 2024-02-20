package com.wanli.serversystem2023.security;
import com.wanli.serversystem2023.entity.User;
import com.wanli.serversystem2023.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.TreeSet;

@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getUserByusername(username);
        if(user == null){
            throw new UsernameNotFoundException("查询不到该用户数据");
        }
        //TODO:获得用户登录权限
        return new AccountUser(user.getId(),user.getUsername(),user.getPassword(),grantedAuthorities(user.getId()));
    }

    public List<GrantedAuthority> grantedAuthorities(Long userId){
        String userauth = userService.getUserAuthorityInfo(userId);
        //将权限字符串按照,分割转换为SpringSecurity所能识别的集合类型
        return AuthorityUtils.commaSeparatedStringToAuthorityList(userauth);
    }

}
