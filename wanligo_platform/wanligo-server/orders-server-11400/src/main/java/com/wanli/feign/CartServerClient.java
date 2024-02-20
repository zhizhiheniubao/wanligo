package com.wanli.feign;

import com.alibaba.fastjson.JSONObject;
import com.wanli.entity.Cart;
import com.wanli.result.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="cart-server")
public interface CartServerClient {
    @GetMapping("/cart/listCart/{accountId}/{businessId}")
    public JSONObject listCart(@PathVariable("accountId") String accoundId, @PathVariable("businessId") Long businessId);

    @PostMapping("/cart/removeCart")
    public JSONObject removeCart(@RequestBody Cart cart);
}

