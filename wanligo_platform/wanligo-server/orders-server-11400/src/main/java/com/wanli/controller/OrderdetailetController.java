package com.wanli.controller;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wanli.base.BaseController;
import com.wanli.entity.Food;
import com.wanli.entity.Orderdetailet;
import com.wanli.feign.FoodServerClient;
import com.wanli.service.OrderdetailetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/orderdetailet")
public class OrderdetailetController extends BaseController {
    @Autowired
    OrderdetailetService orderdetailetService;
    @Autowired
    FoodServerClient foodServerClient;

    @GetMapping("/list/{orderId}")
    public List<Orderdetailet> list(@PathVariable Long orderId){
        List<Orderdetailet> list = orderdetailetService.list(new QueryWrapper<Orderdetailet>().eq("order_id", orderId));
        list.stream().forEach( od ->{
            JSONObject json = foodServerClient.info(od.getFoodId());
            Food food = json.getJSONObject("data").getJSONObject("food").toJavaObject(Food.class);
            od.setFood(food);
        } );
        return list;
    }
}
