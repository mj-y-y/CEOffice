package com.ma.service.impl;

import com.ma.pojo.Nation;
import com.ma.mapper.NationMapper;
import com.ma.service.INationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
