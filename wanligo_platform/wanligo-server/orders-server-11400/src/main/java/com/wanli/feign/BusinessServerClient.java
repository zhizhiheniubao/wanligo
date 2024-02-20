package com.wanli.feign;
import com.alibaba.fastjson.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="business-server")
public interface BusinessServerClient {
    //根据businessId查询商家详情数据
    @GetMapping("/business/info/{businessId}")
    public JSONObject info(@PathVariable("businessId") Long businessId);
}
