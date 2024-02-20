package com.wanli.service.impl;

import com.wanli.entity.Account;
import com.wanli.mapper.AccountMapper;
import com.wanli.service.AccountService;
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
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements AccountService {

}
