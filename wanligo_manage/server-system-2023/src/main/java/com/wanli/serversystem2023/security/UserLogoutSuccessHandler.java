package com.wanli.serversystem2023.security;

import cn.hutool.json.JSONUtil;
import com.wanli.serversystem2023.common.Result;
import com.wanli.serversystem2023.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class UserLogoutSuccessHandler implements LogoutSuccessHandler {
    @Autowired
    JwtUtil jwtUtil;
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //登出操作成功处理代码：
        if(authentication!=null){
            new SecurityContextLogoutHandler().logout(request,response,authentication);
        };
        response.setContentType("application/json;charset=utf-8");
        ServletOutputStream out = response.getOutputStream();
        response.setHeader(jwtUtil.getHeader(),"");
        Result result = Result.success("登出操作成功");
        out.write(JSONUtil.toJsonStr(result).getBytes("utf-8"));

        out.flush();
        out.close();
    }
}
