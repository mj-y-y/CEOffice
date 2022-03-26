package com.ma.server.controller;

import com.ma.server.pojo.Admin;
import com.ma.server.pojo.AdminLoginParam;
import com.ma.server.pojo.RespBean;
import com.ma.server.service.IAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * @Date 2022/3/23 23:32
 * @Since 1.8
 * @Description 登录
 **/

@Api(tags = "LoginController")
@RestController
public class LoginController {

    @Autowired
    private IAdminService adminService;

    @ApiOperation(value = "登录之后返回token")
    @PostMapping("/login")
    public RespBean login(AdminLoginParam adminLoginParam, HttpServletRequest request) {
        return adminService.login(adminLoginParam.getUsername(), adminLoginParam.getPassword(), request);
    }

    @ApiModelProperty("获取当前登录用户信息")
    @GetMapping("/admin/info")
    public Admin getAdminInfo(Principal principal) {
        if (null == principal) {
            return null;
        }
        //在Security 全局中获取用户名
        String userName = principal.getName();
        Admin admin = adminService.getAdminByUserName(userName);
        //前端不用从后端获取密码，并且防止有人恶意破解密码
        admin.setPassword(null);
        return  admin;
    }

    @ApiModelProperty(value = "退出登录")
    @PostMapping("/logout")
    public RespBean logout() {
        return RespBean.success("注销成功！");
    }
}
