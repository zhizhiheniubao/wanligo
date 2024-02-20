package com.wanli.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.wanli.base.BaseController;
import com.wanli.entity.*;
import com.wanli.feign.BusinessServerClient;
import com.wanli.feign.CartServerClient;
import com.wanli.result.R;
import com.wanli.service.OrderdetailetService;
import com.wanli.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrdersController extends BaseController {
    @Autowired
    CartServerClient cartServerClient;
    @Autowired
    OrdersService ordersService;
    @Autowired
    OrderdetailetService orderdetailetService;
    @Autowired
    BusinessServerClient businessServerClient;
    @Autowired
    OrderdetailetController odController;

    //通过用户返回订单数量
    @GetMapping("/countByAccount/{accountId}")
    public R countByAccount(@PathVariable("accountId") String accountId) {
        int count = ordersService.count(new QueryWrapper<Orders>().eq("account_id", accountId).eq("del_tag", 1));
        return R.ok().data("order", count);
    }

    @PostMapping("/confirm/{orderId}")
    public R confirm(@PathVariable("orderId") Long orderId) {
        ordersService.update(null, new UpdateWrapper<Orders>().eq("order_id", orderId).set("order_state", 1));
        return R.ok();
    }

    @GetMapping("/info/{orderId}")
    public R info(@PathVariable Long orderId) {
        Orders orders = ordersService.getById(orderId);
        //设置orders商家对象
        JSONObject json = businessServerClient.info(orders.getBusinessId());
        //{code:1,message:'xx',data:{business:{XXXXXXXXXXXXXXXXXXXXXXX}}}
        Business business = json.getJSONObject("data").getJSONObject("business").toJavaObject(Business.class);
        orders.setBusiness(business);  //使用orders中的商家编号businessId查询到商家对象数据
        //设置orders的订单详情list
        List<Orderdetailet> od_list = odController.list(orderId);
        //System.out.println(od_list);
        orders.setList(od_list);
        return R.ok().data("orders", orders);
    }

    @PostMapping("/save")
    public R save(@RequestBody Orders orders) {
        //1 查询当前用户购物车中当前购买商家的所有餐品
        JSONObject json = cartServerClient.listCart(orders.getAccountId(), orders.getBusinessId());
        System.out.println(json);
        //List<Cart>
        List<Cart> cartList = json.getJSONObject("data").getJSONArray("cartList").toJavaList(Cart.class);

        //2 创建订单
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String dateStr = sdf.format(d);
        orders.setOrderDate(dateStr);

        ordersService.save(orders);  //save成功订单，主键自增，orders.getOrderId()
        Long orderId = orders.getOrderId();

        //3 批量插入订单明细
        List<Orderdetailet> list = new ArrayList<>();
        for (Cart c : cartList) {
            Orderdetailet od = new Orderdetailet();
            od.setOrderId(orderId);
            od.setFoodId(c.getFoodId());
            od.setQuantity(c.getQuantity());
            list.add(od);
        }
        orderdetailetService.saveBatch(list);
        //4 调用远程购物车服务，将已经生成订单的购物车数据删除
        Cart cart = new Cart();
        cart.setBusinessId(orders.getBusinessId());
        cart.setAccountId(orders.getAccountId());
        cartServerClient.removeCart(cart);
        //5 创建订单成功， 将订单编号返回vue
        return R.ok().data("orderId", orders.getOrderId());
    }

    @GetMapping("/paid/{accountId}")
    public R getPaidOrders(@PathVariable String accountId) {
        List<Orders> paidOrders = ordersService.list(new QueryWrapper<Orders>().eq("del_tag", 1).eq("account_id", accountId).eq("order_state", 1));
        for (Orders order : paidOrders) {
            JSONObject json = businessServerClient.info(order.getBusinessId());
            Business business = json.getJSONObject("data").getJSONObject("business").toJavaObject(Business.class);
            order.setBusiness(business);
            // 获取订单的食物数据
            List<Orderdetailet> od_list = odController.list(order.getOrderId());
            order.setList(od_list);
        }
        Collections.reverse(paidOrders);
        return R.ok().data("orders", paidOrders);
    }

    @GetMapping("/unpaid/{accountId}")
    public R getUnpaidOrders(@PathVariable String accountId) {
        List<Orders> unpaidOrders = ordersService.list(new QueryWrapper<Orders>().eq("del_tag", 1).eq("account_id", accountId).eq("order_state", 0));
        for (Orders order : unpaidOrders) {
            JSONObject json = businessServerClient.info(order.getBusinessId());
            Business business = json.getJSONObject("data").getJSONObject("business").toJavaObject(Business.class);
            order.setBusiness(business);
            // 获取订单的食物数据
            List<Orderdetailet> od_list = odController.list(order.getOrderId());
            order.setList(od_list);
        }
        Collections.reverse(unpaidOrders);
        return R.ok().data("orders", unpaidOrders);
    }
}
