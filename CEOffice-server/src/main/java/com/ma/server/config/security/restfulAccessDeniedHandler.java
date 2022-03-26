package com.ma.server.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ma.server.pojo.RespBean;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Date 2022/3/26 16:11
 * @Since 1.8
 * @Description :
 *当访问接口没有权限是，自定义返回结果
 **/
@Component
public class restfulAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        RespBean bean = RespBean.error("权限不足，请联系管理员！");
        bean.setCode(403);
        out.write(new ObjectMapper().writeValueAsString(bean));
        out.flush();
        out.close();
    }
}
