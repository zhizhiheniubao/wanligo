package com.wanli.serversystem2023.common;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wanli.serversystem2023.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseController {
    //所有控制器父类，自动装配所有控制器要用到的对象
    @Autowired
    protected HttpServletRequest request;
    //Redis操作对象
    @Autowired
    protected RedisUtil redisUtil;

    //项目中其他控制器共同使用对象，全部可以自动装配到这里

    //分页封装的方法，接收前端请求参数：current，size
    public Page getPage(){
        try {
            Integer current = ServletRequestUtils.getIntParameter(request, "current");
            Integer size = ServletRequestUtils.getIntParameter(request, "size");
            return new Page(current,size);
        } catch (ServletRequestBindingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
