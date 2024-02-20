package com.wanli.serversystem2023.security;

import cn.hutool.json.JSONUtil;
import com.wanli.serversystem2023.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class UserAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {

        //登录验证失败执行代码
        response.setContentType("application/json;charset=utf-8");
        ServletOutputStream out = response.getOutputStream();

        log.info("用户权限不足");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        Result fail = Result.fail("权限不足"+e.getMessage());
        //fail对象序列化成JSON数据
        out.write(JSONUtil.toJsonStr(fail).getBytes("utf-8"));

        out.flush();
        out.close();
    }
}
