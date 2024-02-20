package com.wanli.serversystem2023.service.impl;

import com.wanli.serversystem2023.entity.Account;
import com.wanli.serversystem2023.mapper.AccountMapper;
import com.wanli.serversystem2023.service.AccountService;
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
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements AccountService {

}
