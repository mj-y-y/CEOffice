package com.ma.server.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ma.server.mapper.OplogMapper;
import com.ma.server.pojo.Oplog;
import com.ma.server.service.IOplogService;
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
public class OplogServiceImpl extends ServiceImpl<OplogMapper, Oplog> implements IOplogService {

}
