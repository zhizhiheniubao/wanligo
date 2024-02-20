package com.wanli.base;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

public class BaseController {
    @Autowired
    protected HttpServletRequest request;
//    @Autowired
//    protected RestTemplate restTemplate;

    //mybatis-plus分页
    //分页封装的方法，接收前端请求参数：current,size
    public Page getPage(){
        try {
            //获得前端请求 页码参数
            Integer current = ServletRequestUtils.getIntParameter(request, "current");
            //获得前端请求  一页显示条数
            Integer size = ServletRequestUtils.getIntParameter(request, "size");

            return new Page(current,size);
        } catch (ServletRequestBindingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
