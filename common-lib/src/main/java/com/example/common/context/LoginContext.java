package com.example.common.context;

import com.example.common.bo.UserInfoBo;

/**
 * 当前登录用户信息
 *
 * @author Leo
 * @date 2020.02.19
 */
public interface LoginContext {
	/**
	 * 获取当前登录用户
	 */
	UserInfoBo getUser();

	/**
	 * 获取当前登录用户的token
	 */
	String getToken();

	/**
	 * 是否登录
	 */
	boolean hasLogin();
}
