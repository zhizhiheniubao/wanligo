package com.wanli.serversystem2023.controller;



import cn.hutool.core.util.StrUtil;
import cn.hutool.db.sql.Order;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wanli.serversystem2023.common.Result;
import com.wanli.serversystem2023.entity.Account;
import com.wanli.serversystem2023.entity.Orderdetailet;
import com.wanli.serversystem2023.entity.Orders;
import com.wanli.serversystem2023.service.AccountService;
import com.wanli.serversystem2023.service.OrderdetailetService;
import com.wanli.serversystem2023.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import com.wanli.serversystem2023.common.BaseController;

import java.util.ArrayList;
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
@RequestMapping("/orders")
public class OrdersController extends BaseController {
    @Autowired
    OrdersService ordersService;
    @Autowired
    OrderdetailetService orderdetailetService;
    @Autowired
    AccountService accountService;

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
    public Result page(String data,Integer orderState,String endTime,String startTime,Integer deTag) throws Exception {
        QueryWrapper<Orders> wrapper = new QueryWrapper<>();
        if(data != null && data.length() > 0){
            List<Account> accounts = accountService.list(new QueryWrapper<Account>().like(StrUtil.isNotBlank(data), "account_name", data));
            List<Integer> accountIds = new ArrayList<Integer>();
            accounts.forEach(account -> {
                categoryIds.add(account.getUserId());
            });
            wrapper.and(i -> i.like()
            );
        }
        if (orderState != null){
            wrapper.eq("order_state",orderState);
        }
        if (endTime != null){
            wrapper.and(i -> i.ge("order_date",startTime)
                                  .le("order_date",endTime)
            );
        }
        if (deTag != null){
            wrapper.eq("de_tag",deTag);
        }

        Page<Orders> orders = ordersService.page(getPage(), wrapper);
        return Result.success(orders);
    }
}
//        Page<Orders> orders;
//        SimpleDateFormat sdf = new SimpleDateFormat(" yyyy-MM-dd");
//        QueryWrapper<Orders> wrapper = new QueryWrapper<>();
//        if(accountId.length() != 0 || orderState != null || startTime.length() != 0){
//            if(accountId.length() != 0 && orderState != null && startTime.length() != 0){
//                wrapper.and(i -> i.eq("order_state",orderState)
//                                  .like(StrUtil.isNotBlank(accountId),"account_id",accountId)
//                                  .ge("order_date",startTime)
//                                  .le("order_date",endTime)
//                );
//            }else {
//                return Result.fail("查询数据未输入完整");
//            }
//            orders = ordersService.page(getPage(),wrapper);
//        }else {
//            orders = ordersService.page(getPage());
//        return Result.success(orders);