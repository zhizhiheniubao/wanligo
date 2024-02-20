package com.wanli.serversystem2023.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wanli.serversystem2023.entity.Orderdetailet;
import com.wanli.serversystem2023.mapper.OrderdetailetMapper;
import com.wanli.serversystem2023.service.OrderdetailetService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author FJF
 * @since 2023-12-14
 */
@Service
public class OrderdetailetServiceImpl extends ServiceImpl<OrderdetailetMapper, Orderdetailet> implements OrderdetailetService {

    @Override
    public void deleteOrderdetailetByBusinessId(Long[] businessIds) {
        Arrays.stream(businessIds).forEach(businessId -> {
            this.remove(new QueryWrapper<Orderdetailet>().inSql("food_id","select food_id from sys_food where business_id = "+ businessId));
        });
    }
}
