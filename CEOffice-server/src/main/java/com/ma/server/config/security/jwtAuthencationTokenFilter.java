package com.ma.server.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Date 2022/3/26 15:40
 * @Since 1.8
 * @Description
 * Jwt 登录授权过滤器
 **/
public class jwtAuthencationTokenFilter extends OncePerRequestFilter {

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${tokenHead}")
    private String tokenHead;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authheader = request.getHeader(tokenHeader);
        //存在token
        if (null != authheader && authheader.startsWith(tokenHead)) {
            String authToken = authheader.substring(tokenHead.length());
            String username = JwtTokenUtil.getUserNameFromToken(authToken);
            if (null != username && null == SecurityContextHolder.getContext().getAuthentication()) {
                
            }
        }
    }
}
