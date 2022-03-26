package com.ma.server.config.security;

import com.ma.server.pojo.Admin;
import com.ma.server.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @Date 2022/3/24 11:51
 * @Since 1.8
 * @Description
 **/
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private IAdminService adminService;
    private restfulAccessDeniedHandler restfulAccessDeniedHandler;
    private restAuthorizationEntryPoint restAuthorizationEntryPoint;

    /**
     * 编写security的完整配置
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception{

        http.csrf()
                //使用JWT ，不需要csrf，关掉csrf
                .disable()
                //基于token，不需要session
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                //允许登录
                .authorizeRequests()
                .antMatchers("/login","/logout")
                .permitAll()
                //除了login和logout，其他请求都要拦截，进行验证
                .anyRequest()
                .authenticated()
                .and()
                //禁用缓存
                .headers()
                .cacheControl();

        //添加Jwt 登录授权过滤器
        http.addFilterBefore(new jwtAuthencationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        //添加自定义未授权和未登录结果返回
        http.exceptionHandling()
                //403
                .accessDeniedHandler(restfulAccessDeniedHandler)
                //403  未登录
                .authenticationEntryPoint(restAuthorizationEntryPoint);
     }

    /**
     * spring security  会走我们重写的userDetail获取到我们额username，--> 通过passwordEncoder去匹配密码
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }

    /**
     * 重写该方法 这个注解Override就是指重写
     *
     * @return
     */
    @Override
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            Admin admin = adminService.getAdminByUserName(username);
            if (null != admin) {
                return admin;
            }
            return null;
        };
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public jwtAuthencationTokenFilter jwtAuthencationTokenFilter() {
        return new jwtAuthencationTokenFilter();
    }
}
