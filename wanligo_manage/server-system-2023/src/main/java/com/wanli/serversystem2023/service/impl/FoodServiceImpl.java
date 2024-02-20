package com.wanli.serversystem2023.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wanli.serversystem2023.common.Result;
import com.wanli.serversystem2023.entity.Food;
import com.wanli.serversystem2023.entity.Orderdetailet;
import com.wanli.serversystem2023.mapper.FoodMapper;
import com.wanli.serversystem2023.service.FoodService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wanli.serversystem2023.service.OrderdetailetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

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
