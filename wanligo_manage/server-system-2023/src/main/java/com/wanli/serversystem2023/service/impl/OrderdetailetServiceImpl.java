package com.wanli.serversystem2023.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wanli.serversystem2023.entity.Orderdetailet;
import com.wanli.serversystem2023.mapper.OrderdetailetMapper;
import com.wanli.serversystem2023.service.OrderdetailetService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Arrays;
import java.util.List;

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
    public List<Orderdetailet> orderdetailetListByorderId(Integer orderId) {
        List<Orderdetailet> orderdetailetList = this.list(new QueryWrapper<Orderdetailet>().and(i->i.eq("order_id",orderId).eq("del_tag",1)));
        return orderdetailetList;
    }
}
