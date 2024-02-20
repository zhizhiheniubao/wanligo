package com.wanli.feign;
import com.alibaba.fastjson.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
@FeignClient(name="food-server")
public interface FoodServerClient {
    @GetMapping("/food/info/{foodId}")
    public JSONObject info(@PathVariable("foodId") Integer foodId);
}
