package com.wanli.serversystem2023.service.impl;

import com.wanli.serversystem2023.entity.Food;
import com.wanli.serversystem2023.mapper.FoodMapper;
import com.wanli.serversystem2023.service.FoodService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author FJF
 * @since 2023-12-17
 */
@Service
public class FoodServiceImpl extends ServiceImpl<FoodMapper, Food> implements FoodService {

}
