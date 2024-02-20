package com.wanli.service.impl;

import com.wanli.entity.Business;
import com.wanli.mapper.BusinessMapper;
import com.wanli.service.BusinessService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Byterain
 * @since 2023-12-20
 */
@Service
public class BusinessServiceImpl extends ServiceImpl<BusinessMapper, Business> implements BusinessService {

}
