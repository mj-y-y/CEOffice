package com.ma.server.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ma.server.config.security.JwtTokenUtil;
import com.ma.server.mapper.AdminMapper;
import com.ma.server.pojo.Admin;
import com.ma.server.pojo.RespBean;
import com.ma.server.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ZhouBin
 * @since 2022-03-23
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

    @Autowired
    @Resource
    private AdminMapper adminMapper;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    /**
     * 登录之后返回token
     * @param username
     * @param password
     * @param request
     * @return
     */
    @Override
    public RespBean login(String username, String password, HttpServletRequest request) {

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        //用户信息为null或者与用户密码匹配不正确
        if (null == userDetails || passwordEncoder.matches(password, userDetails.getPassword())) {
            return RespBean.error("用户名或密码不正确");
        } //判断是否禁用
        if (!userDetails.isEnabled()) {
            return RespBean.error("账号被禁用，请联系管理员！");
        }

        //更新security 登录用户对象
        UsernamePasswordAuthenticationToken authenticationToken = new
                UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        //生成token
        String token = jwtTokenUtil.generateToken(userDetails);
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead",tokenHead);

        return RespBean.success("登录成功", tokenMap);
    }

    /**
     * 根据用户名获取用户
     * 匹配用户名
     * 查看用户是否禁用
     * @param userName
     * @return
     */
    @Override
    public Admin getAdminByUserName(String userName) {
        return adminMapper.selectOne(new QueryWrapper<Admin>().eq("userName", userName).eq("enabled", true));

    }
}
