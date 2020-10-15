package com.xxxx.server.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xxxx.server.pojo.RespBean;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 处理403权限问题
 *
 * @author zhoubin
 * @since 1.0.0
 */
@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		response.setStatus(403);
		RespBean respBean = new RespBean(403, "权限不足，请联系管理员",null);
		PrintWriter writer = response.getWriter();
		writer.write(new ObjectMapper().writeValueAsString(respBean));
		writer.flush();
		writer.close();
	}
}