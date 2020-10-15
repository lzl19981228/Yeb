package com.xxxx.server.controller;

import com.xxxx.server.pojo.Admin;
import com.xxxx.server.pojo.AdminLoginParam;
import com.xxxx.server.pojo.RespBean;
import com.xxxx.server.service.IAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * 登录
 *
 * @author zhoubin
 * @since 1.0.0
 */
@Api("登录")
@RestController
public class LoginController {

	@Autowired
	private IAdminService adminService;


	@ApiOperation("登录")
	@PostMapping("/login")
	public RespBean login(@RequestBody AdminLoginParam adminLoginParam) {
		return adminService.login(adminLoginParam.getUsername(), adminLoginParam.getPassword());
	}


	@ApiOperation("获取当前登录用户信息")
	@GetMapping("/admin/info")
	public Admin getAdminInfo(Principal principal) {
		if (null == principal) {
			return null;
		}
		String username = principal.getName();
		Admin admin = adminService.getUserByUsername(username);
		admin.setPassword(null);
		return admin;
	}

	@ApiOperation("退出登录")
	@PostMapping("/logout")
	public RespBean logout() {
		return RespBean.success("注销成功！");
	}
}