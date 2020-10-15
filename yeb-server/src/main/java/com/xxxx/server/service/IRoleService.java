package com.xxxx.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxxx.server.pojo.Role;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zhoubin
 * @since 2020-10-15
 */
public interface IRoleService extends IService<Role> {

	/**
	 * 根据用户id查询角色
	 *
	 * @return
	 */
	List<Role> getRolesByAdminId(Integer adminId);

}
