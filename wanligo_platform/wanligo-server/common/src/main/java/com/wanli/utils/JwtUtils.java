package com.wanli.utils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.springframework.stereotype.Component;
import java.util.Date;

@Data
@Component
public class JwtUtils {
    private long expire = 604800;   //过期时间
    private String secret = "byterain";  //设置秘钥
    private String header = "token";  //设置响应头里的key

    //生成JWT，创建JWT负载一些信息。参数username就是生成JWT 面向的用户是谁。
    public String createToken(String username){
        Date nowDate = new Date();   //当前日期
        //过期时间  nowDate.getTime()毫秒单位的当前时间+过期时间(秒)*1000
        Date expireDate = new Date(nowDate.getTime() + expire * 1000);
        //sub  Subject：所面向的用户
        //iat  IssuedAt:签发时间
        //exp  Expiration:过期时间
        return Jwts.builder()
                .setHeaderParam("typ","JWT")
                .setSubject(username)
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS256,secret)
                .compact();
    }

    //验证JWT对不对
    //解析JWT，如果JWT是正确就解析成功，返回Claims。如果返回Claims对象是null，就解析失败。
    public Claims getClaimsByToken(String jwt){
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(jwt).getBody();
    }

    //验证jwt是否过期
    //先要有一个验证是正确token，然后才能判断是否过期
    public boolean isTokenExpired(Claims claims){
        //判断过期时间 是否在当前时间 之前。
        return claims.getExpiration().before(new Date());
    }
}
