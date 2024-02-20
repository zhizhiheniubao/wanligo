package com.wanli.controller;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wanli.base.BaseController;
import com.wanli.entity.Business;
import com.wanli.entity.Cart;
import com.wanli.entity.CartDto;
import com.wanli.entity.Food;
import com.wanli.feign.BusinessServerClient;
import com.wanli.feign.FoodServerClient;
import com.wanli.result.R;
import com.wanli.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController extends BaseController {
    @Autowired
    CartService cartService;
    @Autowired
    FoodServerClient foodServerClient;
    @Autowired
    BusinessServerClient businessServerClient;
    //OpenFeign替代RestTemplate

    @GetMapping("/listInfoByAccountId/{accountId}")
    public R listInfoByAccountId(@PathVariable("accountId") String accountId){
        List<CartDto> cartDtoList = new ArrayList<>();
        List<Cart> cartsForBusiness = cartService.list(new QueryWrapper<Cart>().eq("account_id", accountId).select("DISTINCT business_id"));
        cartsForBusiness.stream().forEach(cart -> {
            CartDto cartDto = new CartDto();
            //根据商家id获取商家信息
            Long businessId = cart.getBusinessId();
            JSONObject business_info = businessServerClient.info(businessId);
            Business business = business_info.getJSONObject("data").getJSONObject("business").toJavaObject(Business.class);
            cartDto.setBusiness(business);
            //获取食物信息
            List<Food> foodList = new ArrayList<>();
            List<Cart> cartsForFood = cartService.list(new QueryWrapper<Cart>().eq("account_id", accountId).eq("business_id", businessId));
            cartsForFood.stream().forEach(cart1 -> {
                JSONObject food_info = foodServerClient.info(cart1.getFoodId());
                Food food = food_info.getJSONObject("data").getJSONObject("food").toJavaObject(Food.class);
                food.setQuantity(cart1.getQuantity());
                foodList.add(food);
            });
            cartDto.setFoodList(foodList);
            cartDtoList.add(cartDto);
        });
        return R.ok().data("cartList",cartDtoList);
    }

    @GetMapping("/listCart/{accountId}/{businessId}")
    public R listCart(@PathVariable("accountId") String accoundId,@PathVariable("businessId") Long businessId){
        QueryWrapper<Cart> qw = new QueryWrapper<>();
        qw.eq("account_id",accoundId);
        qw.eq("business_id",businessId);

        List<Cart> cartList = cartService.list(qw);

        cartList.stream().forEach( cart ->{
            //调用food服务:  根据餐品id，查询具体餐品数据。
            JSONObject food_info = foodServerClient.info(cart.getFoodId());
            System.out.println(food_info);
            Food food = food_info.getJSONObject("data").getJSONObject("food").toJavaObject(Food.class);
            cart.setFood(food);
            //调用business服务，根据businessId查询商家详细信息。
            JSONObject business_info = businessServerClient.info(cart.getBusinessId());
            Business business = business_info.getJSONObject("data").getJSONObject("business").toJavaObject(Business.class);
            cart.setBusiness(business);
        });

        return R.ok().data("cartList",cartList);
    }



    @GetMapping("/list/{accountId}")
    public R list(@PathVariable("accountId") String accountId){
        List<Cart> list = cartService.list(new QueryWrapper<Cart>().eq("account_id", accountId));
        return R.ok().data("cartList",list);
    }

    @PostMapping("/saveCart")
    public R saveCart(@RequestBody Cart cart){
        cartService.save(cart);
        return R.ok();
    }

    @PostMapping("/updateCart")
    public R updateCart(@RequestBody Cart cart){
        //cartService.updateById(cart);
        QueryWrapper<Cart> qw = new QueryWrapper<>();
        qw.eq("food_id",cart.getFoodId());
        qw.eq("business_id",cart.getBusinessId());
        qw.eq("account_id",cart.getAccountId());
        cartService.update(cart,qw);
        return R.ok();
    }

    @PostMapping("/removeCart")
    public R removeCart(@RequestBody Cart cart){
        // where  business_id =100001 and account_id
        QueryWrapper<Cart> qw = new QueryWrapper<>();
        qw.eq(ObjectUtil.isNotEmpty(cart.getFoodId()),"food_id",  cart.getFoodId());
        qw.eq(ObjectUtil.isNotEmpty(cart.getBusinessId()),"business_id",cart.getBusinessId());
        qw.eq(ObjectUtil.isNotEmpty(cart.getAccountId()),"account_id",cart.getAccountId());
        cartService.remove(qw);
        return R.ok();
    }
}
