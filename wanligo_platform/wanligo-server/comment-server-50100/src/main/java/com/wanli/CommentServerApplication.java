package com.wanli;
import com.wanli.entity.Comment;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CommentServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(CommentServerApplication.class,args);
    }
}
