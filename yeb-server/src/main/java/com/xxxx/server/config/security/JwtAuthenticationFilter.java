package com.xxxx.server.config.security;

import com.xxxx.server.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Jwt登录拦截器
 *
 * @author zhoubin
 * @since 1.0.0
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Value("${jwt.tokenHeader}")
	private String tokenHeader;
	@Value("${jwt.tokenHead}")
	private String tokenHead;
	@Autowired
	private JwtUtils jwtUtils;
	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		//获取请求头
		String authToken = request.getHeader(tokenHeader);
		//判断令牌是否存在并且正确
		if (!StringUtils.isEmpty(authToken) && authToken.startsWith(tokenHead)) {
			//获取令牌
			String token = authToken.substring(tokenHead.length());
			//根据令牌获取用户名
			String username = jwtUtils.getUserNameFromToken(token);
			//如果用户名不为空但是认证对象为空
			if (!StringUtils.isEmpty(username) && null == SecurityContextHolder.getContext().getAuthentication()) {
				//根据用户名获取用户对象
				UserDetails userDetails = userDetailsService.loadUserByUsername(username);
				//判断token是否有效
				if (jwtUtils.validateToken(token, userDetails)) {
					//给SpringSecurity全局上下文设置当前登录用户对象
					UsernamePasswordAuthenticationToken authenticationToken =
							new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
					WebAuthenticationDetails webAuthenticationDetails =
							new WebAuthenticationDetailsSource().buildDetails(request);
					authenticationToken.setDetails(webAuthenticationDetails);
					SecurityContextHolder.getContext().setAuthentication(authenticationToken);
				}
			}
		}
		//放行
		filterChain.doFilter(request,response);
	}
}