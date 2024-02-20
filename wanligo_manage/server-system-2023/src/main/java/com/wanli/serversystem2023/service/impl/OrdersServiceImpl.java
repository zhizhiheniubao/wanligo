package com.wanli.serversystem2023.service.impl;

import com.wanli.serversystem2023.entity.Orders;
import com.wanli.serversystem2023.mapper.OrdersMapper;
import com.wanli.serversystem2023.service.OrdersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author FJF
 * @since 2023-12-14
 */
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {

}
