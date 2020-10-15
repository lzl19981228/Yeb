package com.xxxx.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxxx.server.mapper.DepartmentMapper;
import com.xxxx.server.pojo.Department;
import com.xxxx.server.service.IDepartmentService;
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
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements IDepartmentService {

}
