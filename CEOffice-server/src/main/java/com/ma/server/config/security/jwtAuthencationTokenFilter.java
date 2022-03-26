package com.ma.server.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
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
 * Jwt
 **/
public class jwtAuthencationTokenFilter extends OncePerRequestFilter {

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${tokenHead}")
    private String tokenHead;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authheader = request.getHeader(tokenHeader);
        //存在token
        if (null != authheader && authheader.startsWith(tokenHead)) {
            String authToken = authheader.substring(tokenHead.length());
            String username = JwtTokenUtil.getUserNameFromToken(authToken);
            //用户名（token）存在，但是未登录
            //要重新设置到Spring Security的全局上下文对象中 非常重要！！！
            if (null != username && null == SecurityContextHolder.getContext().getAuthentication()) {
                //通过loadUserByUsername 拿到用户对象，相当于登录了
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                //验证token是否有效，重新设置用户对象
                if ( jwtTokenUtil.validateToken(tokenHead,userDetails)) {
                    UsernamePasswordAuthenticationToken authenticationToken = new
                            UsernamePasswordAuthenticationToken(null, userDetails.getAuthorities());
                    //重新设置到用户对象
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }
        //放行
        filterChain.doFilter(request,response);
    }
}
