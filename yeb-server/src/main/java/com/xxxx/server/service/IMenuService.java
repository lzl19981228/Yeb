package com.xxxx.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxxx.server.pojo.Menu;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zhoubin
 * @since 2020-10-15
 */
public interface IMenuService extends IService<Menu> {

	/**
	 * 根据当前用户获取菜单
	 *
	 * @return
	 */
	List<Menu> getMenusByAdminId();


	/**
	 * 查询所有菜单及对应角色
	 *
	 * @return
	 */
	List<Menu> getMenusWithRoles();
}
