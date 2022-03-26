package com.ma.server.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ma.server.mapper.NationMapper;
import com.ma.server.pojo.Nation;
import com.ma.server.service.INationService;
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
public class NationServiceImpl extends ServiceImpl<NationMapper, Nation> implements INationService {

}
