package com.wanli.serversystem2023.controller;



import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.internal.$Gson$Types;
import com.wanli.serversystem2023.common.Result;
import com.wanli.serversystem2023.entity.*;
import com.wanli.serversystem2023.service.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import com.wanli.serversystem2023.common.BaseController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author FJF
 * @since 2023-12-14
 */
@RestController
@RequestMapping("/orders")
public class OrdersController extends BaseController {
    @Autowired
    OrdersService ordersService;
    @Autowired
    OrderdetailetService orderdetailetService;
    @Autowired
    AccountService accountService;
    @Autowired
    BusinessService businessService;
    @Autowired
    DeliveryaddressService deliveryaddressService;
    @Autowired
    FoodService foodService;

    @GetMapping("/ordersGroupBy")
    public Result ordersGroupBy(){
        List<Map<String, Object>> groupBys = new ArrayList<Map<String, Object>>();
        groupBys = ordersService.listMaps(new QueryWrapper<Orders>().select("count(*) as count,business_id as businessId").groupBy("business_id"));
        return Result.success(groupBys);
    }

    @GetMapping("/select")
    public Result select(String accountId,Integer orderState,String endTime,String startTime){
        QueryWrapper<Orders> wrapper = new QueryWrapper<>();
        wrapper.and(i -> i.eq("order_state",orderState).like(StrUtil.isNotBlank(accountId),"account_id",accountId));
        Page<Orders> orders = ordersService.page(getPage(),wrapper);
        return Result.success(orders);
    }

    @PostMapping("/delete")
    public Result delete(@RequestBody Long[] ordersIds){
        ordersService.update(null,new UpdateWrapper<Orders>().in("order_id",ordersIds)
                                                                    .set("del_tag",0));
//
        return Result.success("订单删除成功");
    }

    @GetMapping("/list")
    public Result list(){
        List<Orders> orders = ordersService.list();
        return Result.success(orders);
    }

    @GetMapping("/page")
    public Result page(String data,Integer orderState,String endTime,String startTime,Long delTag){
        System.out.println("data"+data);
        QueryWrapper<Orders> ordersWrapper = new QueryWrapper<>();
        if(data != null && data.length() > 0){
            List<Account> accounts = accountService.list(new QueryWrapper<Account>().like(StrUtil.isNotBlank(data), "account_name", data));
            List<String> accountIds = new ArrayList<String>();
            if (accounts.size() > 0){
                accounts.forEach(account -> { accountIds.add(account.getAccountId()); });
            }else{
                accountIds.add("0");
            }

            List<Deliveryaddress> deliveryaddresses = deliveryaddressService.list(new QueryWrapper<Deliveryaddress>().like(StrUtil.isNotBlank(data), "address", data));
            List<Integer> deliveryaddressIds = new ArrayList<>();
            if (deliveryaddresses.size() > 0){
                deliveryaddresses.forEach(deliveryaddress -> { deliveryaddressIds.add(deliveryaddress.getDaId()); });
            } else {
                deliveryaddressIds.add(0);
            }

            List<Business> businesses = businessService.list(new QueryWrapper<Business>().like(StrUtil.isNotBlank(data), "business_name", data));
            List<Long> businessIds = new ArrayList<>();
            if (businesses.size() > 0){
                businesses.forEach(business -> { businessIds.add(business.getBusinessId()); });
            } else {
                businessIds.add(0L);
            }

            ordersWrapper.and(i -> i.in("account_id",accountIds)
                                    .or()
                                    .in("business_id",businessIds)
                                    .or()
                                    .in("da_id",deliveryaddressIds)
            );
        }
        if (orderState != null){
            ordersWrapper.eq("order_state",orderState);
        }
        if (endTime != null && endTime.length() > 0){
            ordersWrapper.and(i -> i.ge("order_date",startTime)
                                  .le("order_date",endTime)
            );
        }
        if (delTag != null){
            ordersWrapper.eq("del_tag",delTag);
        }

        Page<Orders> orders = ordersService.page(getPage(),ordersWrapper);
        return Result.success(orders);
    }
}