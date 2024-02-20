package com.wanli.serversystem2023.service;

import com.wanli.serversystem2023.entity.Orderdetailet;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author FJF
 * @since 2023-12-14
 */
public interface OrderdetailetService extends IService<Orderdetailet> {
    public List<Orderdetailet> orderdetailetListByorderId(Integer orderId);
}
