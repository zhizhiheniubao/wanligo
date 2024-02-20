package com.wanli.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
        import com.wanli.base.BaseController;
        import com.wanli.entity.Comment;
        import com.wanli.entity.Deliveryaddress;
        import com.wanli.service.DeliveryaddressService;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.web.bind.annotation.*;
        import com.wanli.result.R;
        import java.util.List;

@RestController
@RequestMapping("/deliveryaddress")
public class DeliveryaddressController extends BaseController {

    @Autowired
    DeliveryaddressService addressService;

    //通过用户返回配送地址数量
    @GetMapping("/countByAccount/{accountId}")
    public R countByAccount(@PathVariable("accountId") String accountId){
        int count = addressService.count(new QueryWrapper<Deliveryaddress>().eq("account_id", accountId));
        return R.ok().data("address",count);
    }

    @GetMapping("/listAddressByAccountId/{accountId}")
    public R list(@PathVariable String accountId){
        List<Deliveryaddress> list = addressService.list(new QueryWrapper<Deliveryaddress>().eq("account_id", accountId));
        return R.ok().data("addressList",list);
    }

    @PostMapping("/save")
    public R save(@RequestBody Deliveryaddress deliveryaddress){
        addressService.save(deliveryaddress);
        return R.ok();
    }

    @PostMapping("/update")
    public R update(@RequestBody Deliveryaddress deliveryaddress){
        QueryWrapper<Deliveryaddress> qw = new QueryWrapper<>();
        qw.eq("da_id",deliveryaddress.getDaId());
        addressService.update(deliveryaddress,qw);
        return R.ok();
    }

    @GetMapping("/info/{daId}")
    public R info(@PathVariable("daId") Integer daId){
        Deliveryaddress address = addressService.getById(daId);
        return R.ok().data("address",address);
    }

    @PostMapping("/delete")
    public R delete(Integer daId){
        addressService.removeById(daId);
        return R.ok();
    }
}
