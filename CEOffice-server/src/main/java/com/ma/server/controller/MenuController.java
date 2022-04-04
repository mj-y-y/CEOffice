package com.ma.server.controller;


import com.ma.server.impl.MenuServiceImpl;
import com.ma.server.pojo.Menu;
import com.ma.server.service.IMenuService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ZhouBin
 * @since 2022-03-23
 */
@RestController
@RequestMapping("/system/cfg")
public class MenuController {
    @Resource
    private IMenuService menuService;

    @ApiOperation(value = "根据用户id查询菜单列表）")
    @GetMapping("/menu")
    public List<Menu> getMenuByAdminId(){
        return menuService.getMenuByAdminId();
    }
}
