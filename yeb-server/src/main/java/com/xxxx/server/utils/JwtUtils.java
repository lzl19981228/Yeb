package com.xxxx.server.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Jwt工具类
 *
 * @author zhoubin
 * @since 1.0.0
 */
@Component
public class JwtUtils {

	private final String CLAIMS_USER = "sub";
	private final String CLAIMS_CREATED = "created";
	@Value("${jwt.secret}")
	private String secret;
	@Value("${jwt.expiration}")
	private Integer expiration;


	/**
	 * 根据用户名生成token
	 *
	 * @param userDetails
	 * @return
	 */
	public String generatorToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		claims.put(CLAIMS_USER, userDetails.getUsername());
		claims.put(CLAIMS_CREATED, new Date());
		return generatorToken(claims);
	}


	/**
	 * 从token中获取用户名
	 * @param token
	 * @return
	 */
	public String getUserNameFromToken(String token){
		Claims claims = getClaimsFromToken(token);
		return claims.getSubject();
	}

	/**
	 * 判断token是否有效
	 * @return
	 */
	public boolean validateToken(String token,UserDetails userDetails){
		String name = getUserNameFromToken(token);
		return name.equals(userDetails.getUsername()) && !isTokenExpire(token);
	}

	/**
	 * 判断token是否失效
	 * @param token
	 * @return
	 */
	private boolean isTokenExpire(String token) {
		Date expiration = getExpirationFromToken(token);
		return expiration.before(new Date());
	}

	/**
	 * 从token中获取失效时间
	 * @param token
	 */
	private Date getExpirationFromToken(String token) {
		Claims claims = getClaimsFromToken(token);
		return claims.getExpiration();
	}


	/**
	 * 根据token获取荷载
	 * @param token
	 * @return
	 */
	private Claims getClaimsFromToken(String token) {
		return Jwts.parser()
				.setSigningKey(secret)
				.parseClaimsJws(token)
				.getBody();
	}


	/**
	 * 根据荷载生成token
	 *
	 * @param claims
	 * @return
	 */
	private String generatorToken(Map<String, Object> claims) {
		return Jwts.builder()
				.addClaims(claims)
				.signWith(SignatureAlgorithm.HS512, secret)
				.setExpiration(generateExpiration())
				.compact();
	}

	/**
	 * 生成过期时间
	 *
	 * @return
	 */
	private Date generateExpiration() {
		return new Date(System.currentTimeMillis() + expiration * 1000);
	}


}