package com.xxxx.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxxx.server.pojo.Role;
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
public interface RoleMapper extends BaseMapper<Role> {

	/**
	 * 根据用户id查询角色
	 *
	 * @return
	 */
	List<Role> getRolesByAdminId(@Param("id") Integer id);
}
