package com.ma.service.impl;

import com.ma.pojo.Employee;
import com.ma.mapper.EmployeeMapper;
import com.ma.service.IEmployeeService;
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
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {

}
