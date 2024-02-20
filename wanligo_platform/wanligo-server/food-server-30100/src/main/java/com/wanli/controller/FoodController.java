package com.wanli.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wanli.base.BaseController;
import com.wanli.entity.Food;
import com.wanli.result.R;
import com.wanli.service.FoodService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
@Slf4j
@RestController
@RequestMapping("/food")
public class FoodController extends BaseController {
    @Autowired
    FoodService foodService;
    @GetMapping("/listByBusinessId/{businessId}")
    public R list(@PathVariable("businessId") Long businessId){
        log.info("访问餐品的控制器/food/listByBusinessId/");
        List<Food> foods = foodService.list(new QueryWrapper<Food>().eq("business_id", businessId));
        return R.ok().data("foodList",foods);
    }

    //根据foodId查询餐品的详细数据
    @GetMapping("/info/{foodId}")
    public R info(@PathVariable("foodId") Integer foodId){
        Food food = foodService.getById(foodId);
        return R.ok().data("food",food);
    }
}
