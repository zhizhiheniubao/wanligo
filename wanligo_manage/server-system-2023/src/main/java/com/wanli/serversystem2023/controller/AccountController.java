package com.wanli.serversystem2023.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wanli.serversystem2023.common.Result;
import com.wanli.serversystem2023.entity.Account;
import com.wanli.serversystem2023.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.wanli.serversystem2023.common.BaseController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author FJF
 * @since 2023-12-17
 */
@RestController
@RequestMapping("/account")
public class AccountController extends BaseController {
    @Autowired
    AccountService accountService;

    @GetMapping("/list")
    public Result list(){
        List<Account> accounts = accountService.list();
        return Result.success(accounts);
    }

    @GetMapping("/page")
    public Result list(String name) {
        Page<Account> accounts = (Page)this.accountService.page(this.getPage(), (new QueryWrapper()).like(StrUtil.isNotBlank(name), "account_name", name));
        return Result.success(accounts);
    }

    @DeleteMapping({"/{accountId}"})
    public Result deleteAccount(@PathVariable String accountId) {
        Account account = new Account();
        account.setAccountId(accountId);
        account.setDelTag(0);
        QueryWrapper<Account> wrapper = new QueryWrapper();
        wrapper.eq("account_id", accountId);
        boolean flag = this.accountService.update(account, wrapper);
        return flag ? Result.success(account) : Result.fail("删除失败");
    }

    @PostMapping({"/batchDelete"})
    public Result batchDeleteAccount(@RequestBody List<String> accountIds) {
        Account account = new Account();
        account.setDelTag(0);
        QueryWrapper<Account> wrapper = new QueryWrapper();
        wrapper.in("account_id", accountIds);
        boolean flag = this.accountService.update(account, wrapper);
        return flag ? Result.success(account) : Result.fail("删除失败");
    }

    @GetMapping({"/info/{accountId}"})
    public Result info(@PathVariable("accountId") String accountId) {
        Account account = (Account)this.accountService.getById(accountId);
        return Result.success(account);
    }

    @PostMapping({"/update"})
    public Result update(@RequestBody Account account) {
        this.accountService.updateById(account);
        return Result.success("用户数据更新成功");
    }
}
