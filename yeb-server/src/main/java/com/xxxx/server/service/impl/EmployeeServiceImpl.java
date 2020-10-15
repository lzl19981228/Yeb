package com.xxxx.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxxx.server.mapper.EmployeeMapper;
import com.xxxx.server.pojo.Employee;
import com.xxxx.server.service.IEmployeeService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhoubin
 * @since 2020-10-15
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {

}
