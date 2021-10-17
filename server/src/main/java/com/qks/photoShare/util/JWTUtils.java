package com.qks.photoShare.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.Map;
@Component
public class JWTUtils {

    // 生成密钥
    String key = "0123456789_0123456789_0123456789";
    SecretKey secretKey = new SecretKeySpec(key.getBytes(), SignatureAlgorithm.HS256.getJcaName());

    public String createToken(Map<String, Object> user){
        // 1. 生成 token
        return Jwts.builder()  // 创建 JWT 对象
                .setClaims(user)  // 放入用户参数
//                .setExpiration(new Date(System.currentTimeMillis() + 5*60*1000))  // 过期时间
                .setIssuedAt(new Date(System.currentTimeMillis()))  // 当前时间
                .signWith(secretKey)    // 设置安全密钥（生成签名所需的密钥和算法）
                .compact();
    }

    public Boolean verify(String token) {

//        Claims claims;
//        try {
//            claims = Jwts.parser()
//                    .setSigningKey(this.secretKey)
//                    .parseClaimsJws(token)
//                    .getBody();
//
//            final Date exp = claims.getExpiration();
//            Date now = new Date(System.currentTimeMillis());
//            return now.before(exp);
//        } catch (Exception e) {
//            return false;
//        }

        return true;

    }

    public Claims parser(String token){
        Claims claims = Jwts.parser()     // 创建解析对象
                .setSigningKey(secretKey)   // 设置安全密钥（生成签名所需的密钥和算法）
                .parseClaimsJws(token)  // 解析token
                .getBody();
        return claims;
    }

}