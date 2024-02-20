package com.wanli.serversystem2023.controller;


import com.wanli.serversystem2023.common.Result;
import com.wanli.serversystem2023.entity.Category;
import com.wanli.serversystem2023.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import com.wanli.serversystem2023.common.BaseController;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

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
@RequestMapping("/category")
public class CategoryController extends BaseController {
    @Autowired
    CategoryService categoryService;

    @GetMapping("/list")
    public Result list(){
        List<Category> categories = categoryService.list();
        return Result.success(categories);
    }
}
