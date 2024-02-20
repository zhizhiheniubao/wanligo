package com.wanli.controller;
import com.wanli.base.BaseController;
import com.wanli.entity.Category;
import com.wanli.result.R;
import com.wanli.result.ResultCode;
import com.wanli.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
@RestController
@RequestMapping("/category")
@Slf4j
public class CategoryController extends BaseController {
    @Autowired
    CategoryService categoryService;

    @GetMapping("/list")
    public R list(){
        log.info("访问餐品类别");
        List<Category> list = categoryService.list();
        if(list==null){
            return R.error(ResultCode.DATA_NOT_FOUND,"数据没有查询到");
        }else{
            return R.ok().data("categoryList",list);
        }
    }
}
