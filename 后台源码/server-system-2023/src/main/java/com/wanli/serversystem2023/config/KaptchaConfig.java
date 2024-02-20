package com.wanli.serversystem2023.config;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class KaptchaConfig {
    @Bean
    public DefaultKaptcha producer(){
        //创建验证码生成器：设置验证码参数
        Properties properties = new Properties();
        properties.setProperty(Constants.KAPTCHA_BORDER,"no");  //验证码边框
//        properties.setProperty(Constants.KAPTCHA_BORDER_COLOR,"green");
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_FONT_COLOR,"blue");
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_CHAR_SPACE,"4"); //默认间距2
        properties.setProperty(Constants.KAPTCHA_IMAGE_HEIGHT,"40");  //默认高度50
        properties.setProperty(Constants.KAPTCHA_IMAGE_WIDTH,"120");  //默认宽度200
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_FONT_SIZE,"30"); //默认40

        Config config = new Config(properties);
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }
}
