package com.wanli.controller;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wanli.base.BaseController;
import com.wanli.entity.Business;
import com.wanli.entity.Comment;
import com.wanli.result.R;
import com.wanli.result.ResultCode;
import com.wanli.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/business")
public class BusinessController extends BaseController {
    @Autowired
    BusinessService businessService;

    @GetMapping("/lists")
    public R list() {
        List<Business> list = businessService.list();
        return R.ok().data("businessList", list);
    }
    @PostMapping("/list")
    public R list(String businessName) {
        Page<Business> businessList = businessService.page(getPage(), new QueryWrapper<Business>().like(StrUtil.isNotBlank(businessName), "business_name", businessName).eq("del_tag", 1));
        if (businessList.getSize() > 0) {
            return R.ok().data("businessList", businessList);
        } else {
            return R.error(ResultCode.DATA_NOT_FOUND, "数据没有查到");
        }
    }
    //根据类别编号，查询该类别下所有的商家数据
    @GetMapping("/listByCategoryId/{categoryId}")
    public R listByCategoryId(@PathVariable("categoryId") Integer categoryId){
        List<Business> list = businessService.list(new QueryWrapper<Business>().eq("category_id", categoryId));
        if(list==null){
            return R.error(ResultCode.DATA_NOT_FOUND,"该类别下的商家数据查询失败");
        }else{
            return R.ok().data("businessList",list);
        }
    }

    //根据商家businessId获得该商家的详细数据
    @GetMapping("/info/{businessId}")
    public R info(@PathVariable("businessId") Long businessId){
        Business business = businessService.getById(businessId);
        return R.ok().data("business",business);
    }

    @GetMapping("/update")
    public R update(Comment comment){
        UpdateWrapper<Business> commentUpdateWrapper = new UpdateWrapper<>();
        commentUpdateWrapper.set("grade",comment.getGrade()).eq("business_id",comment.getBusinessId());
        businessService.update(commentUpdateWrapper);
        return R.ok();
    }
}
