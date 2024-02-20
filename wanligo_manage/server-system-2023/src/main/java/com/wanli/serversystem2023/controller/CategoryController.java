package com.wanli.serversystem2023.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.wanli.serversystem2023.common.Result;
import com.wanli.serversystem2023.entity.Category;
import com.wanli.serversystem2023.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("save")
    public Result save(@RequestBody Category category) {
//        QueryWrapper<Category> categoryQueryWrapper = new QueryWrapper<>();
//        Category category_name = categoryService.getOne(categoryQueryWrapper.eq("category_name", category.getCategoryName()));
//        if (ObjectUtil.isNull(category_name)) {
//            return Result.error(400, "类别已存在");
//        }
//        if (StringUtils.isBlank(category.getCategoryCover())) {
//            return Result.error(400, "");
//        }
        categoryService.save(category);
        return Result.success("添加类别成功");
    }

    @PostMapping("/update")
    public Result update(@RequestBody Category category) {
        categoryService.updateById(category);
        return Result.success("类别数据更新成功");
    }

    //    @DeleteMapping("/{id}")
//    public Result delete(@PathVariable Integer id) {
//        categoryService.removeById(id);
//        return Result.success("删除类别数据成功");
//    }
    //逻辑删除
    @PostMapping("/delete")
    public Result delete(@RequestBody Long[] ids) {
        UpdateWrapper<Category> categoryUpdateWrapper = new UpdateWrapper<>();
        categoryUpdateWrapper.set("del_tag",0).in("category_id", ids);
        categoryService.update(categoryUpdateWrapper);
        return Result.success("类别信息批量删除成功");
    }

    @GetMapping("/page")
    public Result findAll(String name) {
        Page<Category> categoryPage = categoryService.page(getPage(), new QueryWrapper<Category>().like(StrUtil.isNotBlank(name), "category_name", name) );
        return Result.success(categoryPage);
    }

    @GetMapping("info/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(categoryService.getById(id));
    }

    @GetMapping("/list")
    public Result list(){
        List<Category> categories = categoryService.list();
        return Result.success(categories);
    }
}
