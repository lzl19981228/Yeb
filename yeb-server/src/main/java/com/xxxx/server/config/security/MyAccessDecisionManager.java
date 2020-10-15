package com.xxxx.server.config.security;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * 动态权限控制
 * 判断之前url查询的所需角色是否和用户角色匹配
 *
 * @author zhoubin
 * @since 1.0.0
 */
@Component
public class MyAccessDecisionManager implements AccessDecisionManager {

	@Override
	public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
		for (ConfigAttribute configAttribute : configAttributes) {
			//获取当前url所需角色
			String needRole = configAttribute.getAttribute();
			//判断所需角色是否和自定义角色相等
			if (needRole.equals("ROLE_Login")){
				//判断当前认证对象是否为匿名
				if (authentication instanceof AnonymousAuthenticationToken){
					throw new AccessDeniedException("尚未登录，请登录");
				}else {
					return;
				}
			}
			//获取当前登录用户角色
			Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
			for (GrantedAuthority authority : authorities) {
				if (needRole.equals(authority.getAuthority())) {
					return;
				}
			}
		}
		throw new AccessDeniedException("权限不足，请联系系统管理员！");
	}

	@Override
	public boolean supports(ConfigAttribute attribute) {
		return true;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}
}