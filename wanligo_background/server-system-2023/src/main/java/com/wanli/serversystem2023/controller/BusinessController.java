package com.wanli.serversystem2023.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wanli.serversystem2023.common.Result;
import com.wanli.serversystem2023.entity.*;
import com.wanli.serversystem2023.service.*;
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
 * @since 2023-12-17
 */
@RestController
@RequestMapping("/business")
public class BusinessController extends BaseController {
    @Autowired
    BusinessService businessService;
    @Autowired
    FoodService foodService;
    @Autowired
    CategoryService categoryService;

    @PostMapping("/delete")
    public Result delete(@RequestBody Long[] businessIds){
        businessService.update(null,new UpdateWrapper<Business>().in("business_id",businessIds)
                            .set("del_tag",0));
        foodService.update(null,new UpdateWrapper<Food>().in("business_id",businessIds)
                .set("del_tag",0));
        return Result.success("商家删除成功");
    }

    @PostMapping("/update")
    public Result update(@RequestBody Business business){
        businessService.updateById(business);
        return Result.success("商家数据更新成功");
    }

    @PostMapping("/save")
    public Result save(@RequestBody Business business){
        businessService.save(business);
        return Result.success("商家数据录入成功");
    }

    @GetMapping("/info/{id}")
    public Result info(@PathVariable("id") Long id){
        Business business = businessService.getById(id);
        return Result.success(business);
    }

    @GetMapping("/page")
    public Result page(String data,Long categoryId,Long delTag){
        System.out.println("data"+data);
        QueryWrapper<Business> businessWrapper = new QueryWrapper<>();
        if(data != null && data.length() > 0){
            List<Category> categorys = categoryService.list(new QueryWrapper<Category>().like(StrUtil.isNotBlank(data), "category_name", data));
            List<Integer> categoryIds = new ArrayList<Integer>();
                        categorys.forEach(category -> {
                categoryIds.add(category.getCategoryId());
            });
            businessWrapper.and(i -> i.like(StrUtil.isNotBlank(data),"business_name",data)
                                        .or()
                                        .like(StrUtil.isNotBlank(data),"business_address",data)
                                        .or()
                                        .like(StrUtil.isNotBlank(data),"business_explain",data)
                                        .or()
                                        .in("category_id",categoryIds)
            );
        }
        if(categoryId != null){
            businessWrapper.eq("category_id",categoryId);
        }
        if(delTag != null){
            businessWrapper.eq("del_tag",delTag);
        }
        Page<Business> businesses = businessService.page(getPage(),businessWrapper);
        return Result.success(businesses);
    }

    @GetMapping("/list")
    public Result list(){
        List<Business> businesses = businessService.list();
        return Result.success(businesses);
    }
}
