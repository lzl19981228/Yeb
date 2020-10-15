package com.xxxx.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxxx.server.mapper.AdminMapper;
import com.xxxx.server.pojo.Admin;
import com.xxxx.server.pojo.RespBean;
import com.xxxx.server.service.IAdminService;
import com.xxxx.server.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhoubin
 * @since 2020-10-15
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private JwtUtils jwtUtils;
	@Value("${jwt.tokenHead}")
	private String tokenHead;
	@Autowired
	private AdminMapper adminMapper;

	/**
	 * 登录
	 * @param username
	 * @param password
	 * @return
	 */
	@Override
	public RespBean login(String username, String password) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		//判断用户是否存在
		if (null==userDetails||!passwordEncoder.matches(password,userDetails.getPassword())){
			throw new UsernameNotFoundException("用户名或密码不正确");
		}
		//判断用户是否启用
		if (!userDetails.isEnabled()){
			return RespBean.error("用户尚未启用，请联系管理员");
		}
		//将当前用户对象存入SpringSecurity全局上下文，方便其他方法使用
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,
				null, userDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		//生成token并返回
		String token = jwtUtils.generatorToken(userDetails);
		Map<String,Object> tokenMap = new HashMap<>();
		tokenMap.put("tokenHead",tokenHead);
		tokenMap.put("token",token);
		return RespBean.success("登录成功",tokenMap);
	}

	/**
	 * 根据用户名获取用户信息
	 * @param username
	 * @return
	 */
	@Override
	public Admin getUserByUsername(String username) {
		return adminMapper.selectOne(new QueryWrapper<Admin>().eq("username",username));
	}
}
