package com.wanli.serversystem2023.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wanli.serversystem2023.common.Result;
import com.wanli.serversystem2023.entity.Food;
import com.wanli.serversystem2023.entity.Orderdetailet;
import com.wanli.serversystem2023.entity.Orders;
import com.wanli.serversystem2023.service.FoodService;
import com.wanli.serversystem2023.service.OrderdetailetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.wanli.serversystem2023.common.BaseController;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author FJF
 * @since 2023-12-17
 */
@RestController
@RequestMapping("/food")
public class FoodController extends BaseController {
    @Autowired
    OrderdetailetService orderdetailetService;
    @Autowired
    FoodService foodService;

    //根据订单id获取餐品信息
    @GetMapping("/listByOrderId/{orderId}")
    public Result listByOrderId(@PathVariable("orderId") Integer orderId){
        List<Orderdetailet> orderdetailetList = orderdetailetService.orderdetailetListByorderId(orderId);
        List<Food> foodList = new ArrayList<>();
        orderdetailetList.forEach(orderdetailet -> {
            foodList.add(foodService.getById(orderdetailet.getFoodId()));
        });
        return Result.success(foodList);
    }

    //根据商家id获取餐品
    @GetMapping("/listByBusinessId/{businessId}")
    public Result listByBusinessId(@PathVariable("businessId") Integer businessId){
        List<Food> foods = foodService.list(new QueryWrapper<Food>().and(i->i.eq("business_id",businessId).eq("del_tag",1)));
        return Result.success(foods);
    }

    @GetMapping({"/page"})
    public Result list(String name, BigInteger businessId) {
        Page<Food> page = this.getPage();
        IPage foods;
        if (!StrUtil.isNotBlank(name) && businessId == null) {
            foods = this.foodService.page(page);
        } else {
            QueryWrapper<Food> queryWrapper = new QueryWrapper();
            if (StrUtil.isNotBlank(name)) {
                queryWrapper.like("food_name", name);
            }

            if (businessId != null) {
                queryWrapper.eq("business_id", businessId);
            }

            foods = this.foodService.page(page, queryWrapper);
        }

        return Result.success(foods);
    }

    @Transactional
    @PostMapping({"/delete"})
//    @PreAuthorize("hasAuthority('data:food:delete')")
    public Result delete(@RequestBody Long[] ids) {
        this.foodService.removeByIds(Arrays.asList(ids));
        return Result.success("食物信息删除成功");
    }

    @GetMapping({"/info/{foodId}"})
    public Result info(@PathVariable("foodId") Long foodId) {
        Food food = (Food)this.foodService.getById(foodId);
        return Result.success(food);
    }

    @PostMapping({"/save"})
    public Result save(@RequestBody Food food) {
        this.foodService.save(food);
        return Result.success("食物信息保存成功");
    }

    @PostMapping({"/update"})
    public Result update(@RequestBody Food food) {
        this.foodService.updateById(food);
        return Result.success("食物数据更新成功");
    }
}
