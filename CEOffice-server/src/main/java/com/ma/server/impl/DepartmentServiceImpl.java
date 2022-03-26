package com.ma.server.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ma.server.mapper.DepartmentMapper;
import com.ma.server.pojo.Department;
import com.ma.server.service.IDepartmentService;
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
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements IDepartmentService {

}
