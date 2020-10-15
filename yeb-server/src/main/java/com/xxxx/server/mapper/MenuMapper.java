package com.xxxx.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxxx.server.pojo.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhoubin
 * @since 2020-10-15
 */
public interface MenuMapper extends BaseMapper<Menu> {

	/**
	 * 根据当前登录用户获取菜单
	 * @param id
	 * @return
	 */
	List<Menu> getMenusByAdminId(@Param("id") Integer id);

	/**
	 * 查询所有菜单及对应角色
	 *
	 * @return
	 */
	List<Menu> getMenusWithRoles();
}
