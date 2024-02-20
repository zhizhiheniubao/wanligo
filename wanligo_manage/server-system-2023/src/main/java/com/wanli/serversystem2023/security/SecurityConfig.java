package com.wanli.serversystem2023.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    LoginFailureHandler loginFailureHandler;//登陆验证失败处理器
    @Autowired
    LoginSuccessHandler loginSuccessHandler;
    @Autowired
    CaptchaFilter captchaFilter; //验证码过滤器
    @Autowired
    UserDetailsServiceImpl userDetailsService;
    @Autowired
    AuthenticcationEntryPoint authenticcationEntryPoint; //jwt验证失败的处理器
    @Autowired
    UserAccessDeniedHandler userAccessDeniedHandler;
    @Autowired
    UserLogoutSuccessHandler logoutSuccessHandler;

    //配置jwt过滤器，让SpringIOC创建对象
    @Bean
    public JWTAuthenticationFilter jwtAuthenticationFilter() throws Exception{
        return new JWTAuthenticationFilter(authenticationManager());
    }

    @Bean
    public BCryptPasswordEncoder cryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //SpringSecurity安全验证，并不是所有的路径都需要身份权限验证
    //定义路径白名单
    public static final String[] URL_WHIELIST={
            "/api/captcha","/login","/logout"
    };

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
        .formLogin() //启动Vue登录界面，不再使用SpringSecurity
        .failureHandler(loginFailureHandler)//登录失败处理器配置
        .successHandler(loginSuccessHandler)

        .and()
        .logout()
        .logoutSuccessHandler(logoutSuccessHandler)

        /*springSecurity拦截规则
        * authorizeRequests();对所有的请求URL进行springSecurity验证
        * antMatchers() 设置请求放行的负责，采用白名单放行规则
        * permitAll() 对所有人都采用这个规则
        * anyRequest() 表示匹配任意请求URL请求
        * authenticated() 任意请求都必须要被springSecurity验证之后才能访问
        * */
        .and()
        .authorizeRequests()
        .antMatchers(URL_WHIELIST).permitAll()
        .anyRequest().authenticated()

        /*因为项目采用前后分离开发，不使用session，所以禁用session
          sessionManagement().sessionCreationPolicy()设置session状态
          SessionCreationPolicy.STATELESS springSecurity永远不会使用session
          SessionCreationPolicy.ALWAYS 总是创建session
          SessionCreationPolicy.NEVER 不会创建session，但是如果session已经存在，是可以使用session的
          SessionCreationPolicy.IF_REQUIRED springSecurity只会在需要时创建一个session
        */
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) //项目中不适应session会话技术

        .and()
        .exceptionHandling()
        .accessDeniedHandler(userAccessDeniedHandler)
        .authenticationEntryPoint(authenticcationEntryPoint)

        //登录验证校验过滤器
        .and()
        .addFilter(jwtAuthenticationFilter())
        .addFilterBefore(captchaFilter, UsernamePasswordAuthenticationFilter.class);
    }
}