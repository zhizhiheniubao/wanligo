package com.wanli.serversystem2023.security;

import cn.hutool.core.util.StrUtil;
import com.wanli.serversystem2023.entity.User;
import com.wanli.serversystem2023.service.UserService;
import com.wanli.serversystem2023.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.TreeSet;

@Slf4j
public class JWTAuthenticationFilter extends BasicAuthenticationFilter {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserService userService;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        //验证token代码，
        //从前端请求头中获得token
        String jwt = request.getHeader("token");
        log.info("JWT校验过滤器执行，token={}",jwt);

        //判断token是否为空，是就放行
        if(StrUtil.isBlankOrUndefined(jwt)){
            chain.doFilter(request,response);
            return;
        }

        //因为生成token，设置负载信息里包含登录成功的用户名，解析token成功，chaim对象中就存在token负载信息username
        Claims claims = jwtUtil.getClaimsByToken(jwt);
        if(claims == null){
            throw new JwtException("token解析异常");
        }
        if(jwtUtil.isTokenExpired(claims)){
            throw new JwtException("token已经过期");
        }

        //验证成功，获取用户信息
        String username = claims.getSubject();
        log.info("JWR验证成功，用户{}---正在访问后台系统",username);

        //Jwt验证成功，需要加载当前用户权限信息
        User user = userService.getUserByusername(username);
        //UsernamePasswordAuthenticationToken构造方法: 参数1用户名,null,参数3权限
        //TODO：需要获取当前登录访问用户权限信息
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, null, userDetailsService.grantedAuthorities(user.getId()));
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        chain.doFilter(request,response);

    }
}
