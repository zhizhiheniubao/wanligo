package com.wanli.serversystem2023.controller;


import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wanli.serversystem2023.common.Result;
import com.wanli.serversystem2023.entity.Account;
import com.wanli.serversystem2023.entity.Deliveryaddress;
import com.wanli.serversystem2023.service.AccountService;
import com.wanli.serversystem2023.service.DeliveryaddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.wanli.serversystem2023.common.BaseController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author FJF
 * @since 2023-12-15
 */
@RestController
@RequestMapping("/deliveryaddress")
public class DeliveryaddressController extends BaseController {
    @Resource
    private DeliveryaddressService deliveryAddressService;
    @Resource
    private AccountService accountService;

    @GetMapping("/listByAccountId/{accountId}")
    public Result listByAccountId(@PathVariable("accountId") String accountId){
        List<Deliveryaddress> list = deliveryAddressService.list(new QueryWrapper<Deliveryaddress>().eq("account_id", accountId));
        return Result.success(list);
    }

    @GetMapping("/page")
    public Result page(String name) {
        List<Account> accounts = accountService.list(new QueryWrapper<Account>().like(StrUtil.isNotBlank(name), "account_name", name));
        List<String> accountIds = new ArrayList<String>();
        if (accounts.size() > 0){
            accounts.forEach(account -> { accountIds.add(account.getAccountId()); });
        }else{
            accountIds.add("0");
        }
        Page<Deliveryaddress> adds = deliveryAddressService.page(getPage(), new QueryWrapper<Deliveryaddress>()
                .in("account_id",accountIds));
        //根据accountId查询所属客户名
        QueryWrapper<Account> accountQueryWrapper = new QueryWrapper<Account>().inSql("account_id", "select account_id from sys_deliveryaddress");
        List<Account> list = accountService.list(accountQueryWrapper);
        Map<Object, Object> build = MapUtil.builder().put("DeliveryAddress", adds).put("AccountList", list).build();
        return Result.success(build);
    }

    @PostMapping("/save")
    public Result save(@RequestBody Deliveryaddress deliveryaddress) {
//        deliveryaddress.setAccountId(accountService.getOne(new QueryWrapper<>().eq("")));
//        Account accountIdByAccountName = deliveryAddressService.getAccountIdByAccountName(deliveryaddress.getAccountId());
//        deliveryaddress.setAccountId(accountIdByAccountName.getAccountId());
        deliveryAddressService.save(deliveryaddress);
        return Result.success("联系人信息保存成功");
    }

    @PostMapping("/update")
    public Result update(@RequestBody Deliveryaddress deliveryaddress) {
//        Account accountIdByAccountName = deliveryAddressService.getAccountIdByAccountName(deliveryaddress.getAccountId());
//        deliveryaddress.setAccountId(accountIdByAccountName.getAccountId());
        deliveryAddressService.updateById(deliveryaddress);
        return Result.success("联系人信息更新成功");
    }

    @GetMapping("/info/{id}")
    public Result info(@PathVariable("id") Long id) {
        //根据联系人id,查询信息
        Deliveryaddress address = deliveryAddressService.getById(id);
//        address.setAccountId(deliveryaddressService.getAccountNameByAccountId(strNum).getAccountName());
        return Result.success(address);
    }

    @Transactional
    @PostMapping("/delete")
    public Result delete(@RequestBody Long[] ids) {
        deliveryAddressService.removeByIds(Arrays.asList(ids));
        return Result.success("地址信息删除成功");
    }

    @GetMapping("/list")
    public Result list(){
        List<Deliveryaddress> deliveryaddresses = deliveryAddressService.list();
        return Result.success(deliveryaddresses);
    }
}
