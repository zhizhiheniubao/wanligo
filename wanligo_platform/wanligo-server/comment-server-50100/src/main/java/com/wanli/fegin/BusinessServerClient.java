package com.wanli.fegin;

import com.alibaba.fastjson.JSONObject;
import com.wanli.entity.Comment;
import com.wanli.result.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="business-server")  //name=值，就是调用服务名称。nacos中注册服务名。
public interface BusinessServerClient {
    @GetMapping("/update")
    public R update(Comment comment);
}
