package com.xxxx.server.config.security;

import com.xxxx.server.pojo.Menu;
import com.xxxx.server.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;

/**
 * 动态权限控制
 * 根据访问的url查询所需的角色
 *
 * @author zhoubin
 * @since 1.0.0
 */
@Component
public class MySecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

	@Autowired
	private IMenuService menuService;

	AntPathMatcher antPathMatcher = new AntPathMatcher();

	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		//获取请求的url
		String requestUrl = ((FilterInvocation) object).getRequestUrl();
		List<Menu> menus = menuService.getMenusWithRoles();
		for (Menu menu : menus) {
			//判断请求的url是否和菜单url相等,如果相等获取该url所需的角色
			if (antPathMatcher.match(menu.getUrl(),requestUrl)){
				String[] roles = menu.getRoles()
						.stream()
						.map(role -> role.getName())
						.toArray(String[]::new);
				return SecurityConfig.createList(roles);
			}
		}
		//如果请求的url和菜单都不匹配，默认登录即可访问
		return SecurityConfig.createList("ROLE_Login");
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}
}