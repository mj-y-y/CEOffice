package com.ma.server.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ma.server.mapper.RoleMapper;
import com.ma.server.pojo.Role;
import com.ma.server.service.IRoleService;
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
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

}
