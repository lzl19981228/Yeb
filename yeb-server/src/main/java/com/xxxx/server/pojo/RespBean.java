package com.xxxx.server.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 公共返回对象
 *
 * @author zhoubin
 * @since 1.0.0
 */
@Data
@AllArgsConstructor
public class RespBean {

	private Integer code;
	private String message;
	private Object obj;

	/**
	 * 成功返回方法
	 * @param message
	 * @return
	 */
	public static RespBean success(String message){
		return new RespBean(200,message,null);
	}

	/**
	 * 成功返回方法
	 * @param message
	 * @return
	 */
	public static RespBean success(String message,Object obj){
		return new RespBean(200,message,obj);
	}


	/**
	 * 失败返回方法
	 * @param message
	 * @return
	 */
	public static RespBean error(String message){
		return new RespBean(500,message,null);
	}

	/**
	 * 失败返回方法
	 * @param message
	 * @return
	 */
	public static RespBean error(String message,Object obj){
		return new RespBean(500,message,obj);
	}

}