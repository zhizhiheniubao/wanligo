package com.wanli.service.impl;

import com.wanli.entity.Food;
import com.wanli.mapper.FoodMapper;
import com.wanli.service.FoodService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Byterain
 * @since 2023-12-21
 */
@Service
public class FoodServiceImpl extends ServiceImpl<FoodMapper, Food> implements FoodService {

}
