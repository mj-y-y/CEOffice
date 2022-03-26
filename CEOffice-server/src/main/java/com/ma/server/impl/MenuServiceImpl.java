package com.ma.server.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ma.server.mapper.MenuMapper;
import com.ma.server.pojo.Menu;
import com.ma.server.service.IMenuService;
import org.springframework.stereotype.Service;

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

}
