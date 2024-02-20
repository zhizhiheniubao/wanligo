package com.wanli.serversystem2023.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wanli.serversystem2023.common.Result;
import com.wanli.serversystem2023.entity.Deliveryaddress;
import com.wanli.serversystem2023.service.DeliveryaddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import com.wanli.serversystem2023.common.BaseController;

import java.util.List;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author FJF
 * @since 2023-12-15
 */
@RestController
@RequestMapping("/deliveryaddress")
public class DeliveryaddressController extends BaseController {
    @Autowired
    DeliveryaddressService deliveryaddressService;

    @GetMapping("/list")
    public Result list(){
        List<Deliveryaddress> deliveryaddresses = deliveryaddressService.list();
        return Result.success(deliveryaddresses);
    }
}
