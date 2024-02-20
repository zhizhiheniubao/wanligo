package com.wanli.service.impl;

import com.wanli.entity.Cart;
import com.wanli.mapper.CartMapper;
import com.wanli.service.CartService;
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
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart> implements CartService {

}
