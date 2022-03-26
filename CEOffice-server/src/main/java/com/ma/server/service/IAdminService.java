package com.ma.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ma.server.pojo.Admin;
import com.ma.server.pojo.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.servlet.http.HttpServletRequest;
import java.security.PrivateKey;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ZhouBin
 * @since 2022-03-23
 */
public interface IAdminService extends IService<Admin> {

    /**
     * 登录之后返回Token
     * @param username
     * @param password
     * @param request
     * @return
     */
    RespBean login(String username, String password, HttpServletRequest request);

    /**
     * 根据用户名获取用户
     * @param userName
     * @return
     */
    Admin getAdminByUserName(String userName);
}
