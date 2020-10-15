package com.xxxx.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxxx.server.pojo.Admin;
import com.xxxx.server.pojo.RespBean;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhoubin
 * @since 2020-10-15
 */
public interface IAdminService extends IService<Admin> {

	/**
	 * 登录
	 * @param username
	 * @param password
	 * @return
	 */
	RespBean login(String username, String password);

	/**
	 * 根据用户名获取用户信息
	 * @param username
	 * @return
	 */
	Admin getUserByUsername(String username);
}
