package com.wanli.serversystem2023.service.impl;

import com.wanli.serversystem2023.entity.Category;
import com.wanli.serversystem2023.mapper.CategoryMapper;
import com.wanli.serversystem2023.service.CategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author FJF
 * @since 2023-12-15
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

}
