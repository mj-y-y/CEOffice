package com.ma.server.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ma.server.mapper.SalaryMapper;
import com.ma.server.pojo.Salary;
import com.ma.server.service.ISalaryService;
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
public class SalaryServiceImpl extends ServiceImpl<SalaryMapper, Salary> implements ISalaryService {

}
