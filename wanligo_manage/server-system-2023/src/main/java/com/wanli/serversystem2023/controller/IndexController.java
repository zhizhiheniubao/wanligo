package com.wanli.serversystem2023.controller;

import cn.hutool.core.codec.Base64Encoder;
import cn.hutool.core.map.MapUtil;
import com.google.code.kaptcha.Producer;
import com.wanli.serversystem2023.common.BaseController;
import com.wanli.serversystem2023.common.Const;
import com.wanli.serversystem2023.common.Result;
import com.wanli.serversystem2023.entity.User;
import com.wanli.serversystem2023.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api")
public class IndexController extends BaseController {
    @Autowired
    private Producer producer;
    @Autowired
    private UserService userService;

    @GetMapping("/userinfo")
    public Result userinfo(Principal principal){
        String username = principal.getName();
        User userByusername = userService.getUserByusername(username);
        return Result.success(userByusername);
    }

    @GetMapping("/captcha")
    public Result captcha() throws IOException{
        //调用验证码对象 产生验证码
        String code = producer.createText();
        //验证码需要存储 redis数据库（key  --  vtalue）
        //生成一个随机key，作为存入redis数据库的一个键
        String key = UUID.randomUUID().toString();

        //TODO：需要将生产的验证码存储到redis数据库，登陆时进行验证
        //存储redis中HASH类型：key(name  value)
        redisUtil.hset(Const.CAPTCHA,key,code,300);

        //将产生的验证码变为图片
        BufferedImage image = producer.createImage(code);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(image,"jpg",out);

        //将图片转化为Base64格式
        Base64Encoder encoder = new Base64Encoder();
        String str="data:image/jpeg;base64,";
        String base64Image = str+=encoder.encode(out.toByteArray());

        //输出日志
        log.info("验证码--{}--{}",key,code);

        return Result.success(MapUtil.builder().put("key",key).put("captchaImg",base64Image).build());
    }


    @PreAuthorize("hasAnyAuthority('ROLE_sys:role:save')")
    @PostMapping("/save")
    public Result save(){
        return Result.success("OK");
    }
}
