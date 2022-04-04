package com.ma.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ma.server.pojo.Menu;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ZhouBin
 * @since 2022-03-23
 */
public interface MenuMapper extends BaseMapper<Menu> {

    //根据用户ID查询对应权限
    List<Menu> getMenuByAdminId(Integer id);

//    List<Menu> getRoleByMenuId();
}
