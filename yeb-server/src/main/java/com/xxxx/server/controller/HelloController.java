package com.xxxx.server.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试Controller
 *
 * @author zhoubin
 * @since 1.0.0
 */
@Api("测试controller")
@RestController
public class HelloController {

	@GetMapping("/hello")
	public String hello() {
		return "hello";
	}

	@GetMapping("/employee/basic/hello")
	public String hello2() {
		return "/employee/basic/hello";
	}


	@GetMapping("/employee/advanced/hello")
	public String hello3() {
		return "/employee/advanced/hello";
	}

}