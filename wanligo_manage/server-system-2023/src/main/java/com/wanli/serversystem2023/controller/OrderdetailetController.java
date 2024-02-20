package com.wanli.serversystem2023.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wanli.serversystem2023.common.Result;
import com.wanli.serversystem2023.entity.Food;
import com.wanli.serversystem2023.entity.Orderdetailet;
import com.wanli.serversystem2023.service.FoodService;
import com.wanli.serversystem2023.service.OrderdetailetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.wanli.serversystem2023.common.BaseController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author FJF
 * @since 2023-12-14
 */
@RestController
@RequestMapping("/orderdetailet")
public class OrderdetailetController extends BaseController {
    @Autowired
    OrderdetailetService orderdetailetService;
    @Autowired
    FoodService foodService;

    @GetMapping("/listByOrderId/{orderId}")
    public Result listByorderId(@PathVariable("orderId") Integer orderId){
        return Result.success(orderdetailetService.orderdetailetListByorderId(orderId));
    }
}
