package com.wanli.serversystem2023.security;

import cn.hutool.json.JSONUtil;
import com.wanli.serversystem2023.common.Result;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        //登录验证失败执行代码
        response.setContentType("application/json;charset=utf-8");
        ServletOutputStream out = response.getOutputStream();

        Result fail = Result.fail("Bad credentials".equals(e.getMessage()) ? "用户名或密码错误" : e.getMessage());
        //fail对象序列化成JSON数据
        out.write(JSONUtil.toJsonStr(fail).getBytes("utf-8"));

        out.flush();
        out.close();
    }
}
