package com.wanli.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.wanli.base.BaseController;
import com.wanli.entity.Account;
import com.wanli.result.R;
import com.wanli.result.ResultCode;
import com.wanli.service.AccountService;
import com.wanli.utils.MD5Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
@Slf4j
public class AccountController extends BaseController {
    @Autowired
    AccountService accountService;

//    @GetMapping("/testServer")
//    public R test(){
//        log.info("用户服务调用 商家服务");
//        //使用restTemplate实现服务调用 ,调用Business-server-20100
//        //return restTemplate.getForObject("http://127.0.0.1:20100/business/list", R.class);
//        return restTemplate.getForObject("http://business-server/business/list",R.class);
//    }

    @PostMapping("/updatePassword")
    public R updatePassword(String originalPassword,String password,String accountId,String accountName,Integer accountSex){
        System.out.println(originalPassword+password+accountId);
        Account account = accountService.getById(accountId);
        String md5Password = MD5Utils.md5(MD5Utils.inputPassToNewPass(originalPassword));
        if (!md5Password.equals(account.getPassword())){
            return R.error(ResultCode.USER_ACCOUNT_ERROR,"原密码输入错误");
        }
        accountService.update(null,new UpdateWrapper<Account>().eq("account_id",accountId).set("password",MD5Utils.md5(MD5Utils.inputPassToNewPass(password))));
        accountService.update(null,new UpdateWrapper<Account>().eq("account_id",accountId).set("account_name",accountName));
        accountService.update(null,new UpdateWrapper<Account>().eq("account_id",accountId).set("account_sex",accountSex));
        return R.ok();
    }

    @PostMapping("/register")
    public R register(@RequestBody Account account){
        System.out.println("aa"+account.toString());
        String md5Password = MD5Utils.md5(MD5Utils.inputPassToNewPass(account.getPassword()));
        account.setPassword(md5Password);
        account.setStatu(1);
        account.setDelTag(1);
        accountService.save(account);
        return R.ok();
    }

    @GetMapping("/getAccountById/{accountId}")
    public R getAccountById(@PathVariable("accountId") String accountId){
        System.out.println("accountID"+accountId);
        Account byId = accountService.getById(accountId);
        if (byId == null){
            return R.ok().data("state",1);
        }
        return R.ok().data("state",0);
    }

    @PostMapping("/login")
    public R login(String accountId,String password){
        log.info("手机号是:{}的用户进行登录操作--",accountId);
        Account account = accountService.getOne(new QueryWrapper<Account>().eq("account_id",accountId).eq("del_tag",1));
        if(account == null){
            return R.error(ResultCode.USER_NOT_EXIST,"手机号不存在");
        }else{
            String md5Password = MD5Utils.md5(MD5Utils.inputPassToNewPass(password));
            if(account.getPassword().equals(md5Password)){
                //statu状态，1可用 0 禁用
                if(account.getStatu() == 0){
                    return R.error(ResultCode.USER_ACCOUNT_FORBIDDEN,"用户被禁用");
                }else{
                    return R.ok().data("account",account);
                }
            }else{
                return R.error(ResultCode.USER_ACCOUNT_ERROR,"输入密码错误");
            }
        }
    }
}
