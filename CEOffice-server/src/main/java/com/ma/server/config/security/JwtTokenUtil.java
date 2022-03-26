package com.ma.server.config.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2022/3/23 15:31
 * @Since 1.8
 * @Description  :JwtToken工具类
 **/
@Component
public class JwtTokenUtil {

    //用户名键值
    private static final String CLAIM_KEY_USERNAME = "sub";
    //用户创建时间
    private static final String CLAIM_KEY_CREATE="created";


    //在配置文件拿：# JWT 加解密使用的密钥
    @Value("${jwt.secret}")
    private static String secret;
    //# JWT的超期限时间（60*60*24）
    @Value("${jwt.expiration}")
    private Long expiration;


    /**
     * 根据用户信息生成token
     * @param userDetails
     * @return
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        claims.put(CLAIM_KEY_CREATE, new Date());
        return generateToken(claims);
    }

    /**
     * 从 token 中获取登录用户名
     * @param token
     * @return
     */
    public static String getUserNameFromToken(String token) {
        String username;
        try {
            Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    /**
     * 判断token是否有效：
     *  1、是否过期
     *  2、token中的用户名与 userDetail 中的用户名是否一致
     * @param token
     * @param userDetails
     * @return
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        String userName = getUserNameFromToken(token);
        return userName.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /**
     * 判断token是否失效： 时间是否过期
     * @param token
     * @return
     */
    private boolean isTokenExpired(String token) {
        Date expireDate = getExpiredDateFromToken(token);
        return expireDate.before(new Date());
    }

    /**
     * 从 token中获取过期时间 （荷载 ，荷载中有时间）
     * @param token
     * @return
     */
    private Date getExpiredDateFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getExpiration();
    }

    /***
     * 判断token是否可以被刷新
     * 过期了可以被刷新，没有不能
     * @param token
     * @return
     */
    public boolean canRefresh(String token) {
        return !isTokenExpired(token);
    }

    /***
     * 刷新 token
     * 将生效时间改成当下，并且生成新的token
     * @param token
     * @return
     */
    public String refreshToken(String token) {
        Claims claims = getClaimsFromToken(token);
        claims.put(CLAIM_KEY_CREATE, new Date());
        return generateToken(claims);
    }

    /**
     * 从 token 中获取荷载
     * @param token
     * @return
     */
    private static Claims getClaimsFromToken(String token) {
        Claims claims = null;
        try {
             claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return claims;
    }


    /***
     * 根据荷载生成 JWT Token
     * @param claims
     * @return
     */
    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.ES512,secret)
                .compact();
    }

    /**
     * 生成 token失效时间:
     * 即当前时间+设置的失效时间
     * @return
     */
    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration*1000);
    }
}
