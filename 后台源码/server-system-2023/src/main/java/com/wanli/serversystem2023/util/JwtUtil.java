package com.wanli.serversystem2023.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import org.springframework.stereotype.Component;

import java.util.Date;

//ConfigurationProperties 用于读取application.yaml配置文件或.properties.获取配置文件中信息
@Data
@Component
@ConfigurationProperties(prefix = "token.jwt")
public class JwtUtil {
    private long expire; //过期时间
    private String secret; //设置密钥
    private String header; //响应头

    //生成JWT，创建JWT负载一些信息。参数username就是生成JWT 负载私有数据
    public String createToken(String username){
        Date nowDate = new Date();
        Date expireDate = new Date(nowDate.getTime() + expire * 1000);
        return Jwts.builder()
                .setHeaderParam("typ","JWT")
                .setSubject(username)
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS256,secret)
                .compact();
    }

    //验证jwt对不对
    //jiexiJWT，如果JWT是正确的就解析成功，返回Claims。如果返回Claims对象是null，就解析失败
    public Claims getClaimsByToken(String jwt){
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(jwt).getBody();
    }

    //验证jwt是否过期
    //先要有一个验证是否正确token，然后才能判断是否过期
    public boolean isTokenExpired(Claims claims){
        //判断过期时间是否在当前时间之前
        return claims.getExpiration().before(new Date());
    }
}
