package com.wanli.feign;
        import com.alibaba.fastjson.JSONObject;
        import org.springframework.cloud.openfeign.FeignClient;
        import org.springframework.web.bind.annotation.GetMapping;
        import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="food-server")  //name=值，就是调用服务名称。nacos中注册服务名。
public interface FoodServerClient {
    //调用food-server这个服务中的哪个方法：和调用food-server中方法的定义是一样的。
    @GetMapping("/food/info/{foodId}")
    public JSONObject info(@PathVariable("foodId") Integer foodId);
}
