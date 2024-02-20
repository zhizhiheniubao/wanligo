package com.wanli.serversystem2023.service;

import com.wanli.serversystem2023.entity.Orderdetailet;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author FJF
 * @since 2023-12-14
 */
public interface OrderdetailetService extends IService<Orderdetailet> {
    //根据商家id删除对应的订单详情数据
    public void deleteOrderdetailetByBusinessId(Long[] businessIds);
}
