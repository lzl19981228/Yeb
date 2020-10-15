package com.xxxx.server.controller;


import com.xxxx.server.pojo.Menu;
import com.xxxx.server.service.IMenuService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhoubin
 * @since 2020-10-15
 */
@RestController
@RequestMapping("/system")
public class MenuController {

	@Autowired
	private IMenuService menuService;


	@ApiOperation("根据当前用户获取菜单")
	@GetMapping("/menu")
	public List<Menu> getMenusByAdminId(){
		return menuService.getMenusByAdminId();
	}

}
