package com.wanli.serversystem2023.security;

import cn.hutool.json.JSONUtil;
import com.wanli.serversystem2023.common.Result;
import com.wanli.serversystem2023.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    JwtUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String username = authentication.getName();

        response.setContentType("application/json;charset=utf-8");
        ServletOutputStream out = response.getOutputStream();

        //TODO:获得登陆成功用户信息

        //TODO:产生token，需要将token加入到响应头，返回Vue端
        String token = jwtUtil.createToken(username);
        //将token存到响应头中返回给Vue端
        response.setHeader(jwtUtil.getHeader(),token);
        log.info("token="+token);

        Result success = Result.success("成功");
        out.write(JSONUtil.toJsonStr(success).getBytes("utf-8"));
        out.flush();
        out.close();
    }
}
