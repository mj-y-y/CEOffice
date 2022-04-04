package com.ma.server.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ma.server.mapper.MenuMapper;
import com.ma.server.pojo.Admin;
import com.ma.server.pojo.Menu;
import com.ma.server.service.IMenuService;
import io.jsonwebtoken.lang.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ZhouBin
 * @since 2022-03-23
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {
    @Resource
    private MenuMapper menuMapper;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    /**
     * 根据用户id查询菜单列表
     * @return
     */
    @Override
    public List<Menu> getMenuByAdminId() {
        Integer adminId = ((Admin) SecurityContextHolder.getContext().getAuthentication()).getId();
        ValueOperations<String, Object> valueOperations = (ValueOperations<String, Object>) redisTemplate.opsForCluster();
        //从redis中获取菜单数据
        List<Menu> menus = (List<Menu>) valueOperations.get("menu_" + adminId);
        //如果为空，去数据库获取
        if (Collections.isEmpty(menus)) {
            menus = menuMapper.getMenuByAdminId(adminId);
//            将数据设置到redis中,然后返回
            valueOperations.set("menu_" + adminId, menus);
        }
        return menus;
    }
}
