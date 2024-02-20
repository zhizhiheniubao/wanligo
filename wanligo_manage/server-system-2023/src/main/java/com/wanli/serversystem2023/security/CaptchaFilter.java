package com.wanli.serversystem2023.security;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.wanli.serversystem2023.common.Const;
import com.wanli.serversystem2023.common.exception.CaptchaException;
import com.wanli.serversystem2023.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class CaptchaFilter extends OncePerRequestFilter {
    private final String loginUri="/login";//配置默认登录请求地址是什么
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private LoginFailureHandler loginFailureHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //Vue程序提交登录：http://127.0.0.1:10001/login
        String uri = request.getRequestURI();
        if(uri.equals(loginUri) && request.getMethod().equals("POST")){
            log.info("获取login链接，正在校验验证码----{}",uri);
            try{
                validate(request);
            }catch (CaptchaException e){
                log.info(e.getMessage());
                //交给（验证码验证）登录验证 交给失败处理器执行
                loginFailureHandler.onAuthenticationFailure(request,response,e);
            }
        }
        //不是登录操作，放行后面的代码
        filterChain.doFilter(request,response);
    }

    //校验验证码
    private void validate(HttpServletRequest request) throws CaptchaException{
        //登录操作，提交请求参数：username=admin&password=123&code=3Dfg8
        String code = request.getParameter("code");
        String key = request.getParameter("key");
        if(StringUtils.isBlank(code)){
            //验证码是null，验证失败，抛出异常(验证码验证失败自定义异常）
            throw new CaptchaException("验证码不能为空");
        }
        //需要从redis取出存储验证码
        if(!redisUtil.hget(Const.CAPTCHA,key).equals(code)){
            throw new CaptchaException("验证码不正确");
        }
        redisUtil.hdel(Const.CAPTCHA,key);
    }
}
