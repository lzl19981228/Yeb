package com.xxxx.server.config.security;

import com.xxxx.server.pojo.Admin;
import com.xxxx.server.service.IAdminService;
import com.xxxx.server.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * SpringSecurity配置类
 *
 * @author zhoubin
 * @since 1.0.0
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private IAdminService adminService;
	@Autowired
	private MyAccessDeniedHandler myAccessDeniedHandler;
	@Autowired
	private MyAuthenticationEntryPoint myAuthenticationEntryPoint;
	@Autowired
	private IRoleService roleService;
	@Autowired
	private MyAccessDecisionManager myAccessDecisionManager;
	@Autowired
	private MySecurityMetadataSource mySecurityMetadataSource;

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(
				"/login",
				"/logout",
				"/js/**",
				"/webjars/**",
				"/swagger-resources/**",
				"/v2/api-docs/**",
				"/doc.html"
		);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//使用了JWT，不需要csrf
		http.csrf().disable()
				//不需要session
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.authorizeRequests()
				// .antMatchers("/login/**", "/logout/**").permitAll()
				.anyRequest().authenticated()
				//动态权限控制
				.withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
					@Override
					public <O extends FilterSecurityInterceptor> O postProcess(O object) {
						object.setSecurityMetadataSource(mySecurityMetadataSource);
						object.setAccessDecisionManager(myAccessDecisionManager);
						return object;
					}
				})
				.and()
				//禁用缓存
				.headers()
				.cacheControl().disable();

		//jwt登录拦截器
		http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
				//异常处理
				.exceptionHandling()
				//403
				.accessDeniedHandler(myAccessDeniedHandler)
				//401
				.authenticationEntryPoint(myAuthenticationEntryPoint);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter();
	}

	@Override
	@Bean
	protected UserDetailsService userDetailsService() {
		return username -> {
			Admin admin = adminService.getUserByUsername(username);
			if (null == admin) {
				throw new UsernameNotFoundException("用户名或密码不正确！");
			}
			//配置角色
			admin.setRoles(roleService.getRolesByAdminId(admin.getId()));
			return admin;
		};
	}
}