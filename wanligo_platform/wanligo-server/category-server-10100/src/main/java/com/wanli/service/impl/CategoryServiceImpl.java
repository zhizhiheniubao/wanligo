package com.wanli.service.impl;

import com.wanli.entity.Category;
import com.wanli.mapper.CategoryMapper;
import com.wanli.service.CategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Byterain
 * @since 2023-12-19
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

}
