package com.xxxx.server.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xxxx.server.pojo.RespBean;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 处理401问题，尚未登录。token失效
 *
 * @author zhoubin
 * @since 1.0.0
 */
@Component
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		response.setStatus(401);
		RespBean respBean = new RespBean(401, "尚未登录，先重新登录",null);
		PrintWriter writer = response.getWriter();
		writer.write(new ObjectMapper().writeValueAsString(respBean));
		writer.flush();
		writer.close();
	}
}