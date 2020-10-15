package com.xxxx.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxxx.server.mapper.RoleMapper;
import com.xxxx.server.pojo.Role;
import com.xxxx.server.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhoubin
 * @since 2020-10-15
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

	@Autowired
	private RoleMapper roleMapper;

	/**
	 * 根据用户id查询角色
	 *
	 * @return
	 */
	@Override
	public List<Role> getRolesByAdminId(Integer adminId) {
		return roleMapper.getRolesByAdminId(adminId);
	}
}
