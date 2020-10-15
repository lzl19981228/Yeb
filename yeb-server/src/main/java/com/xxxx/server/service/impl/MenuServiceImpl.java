package com.xxxx.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxxx.server.mapper.MenuMapper;
import com.xxxx.server.pojo.Admin;
import com.xxxx.server.pojo.Menu;
import com.xxxx.server.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

	@Autowired
	private MenuMapper menuMapper;

	/**
	 * 根据当前用户获取菜单
	 *
	 * @return
	 */
	@Override
	public List<Menu> getMenusByAdminId() {
		return menuMapper.getMenusByAdminId(((Admin)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
	}


	/**
	 * 查询所有菜单及对应角色
	 *
	 * @return
	 */
	@Override
	public List<Menu> getMenusWithRoles() {
		return menuMapper.getMenusWithRoles();
	}
}
