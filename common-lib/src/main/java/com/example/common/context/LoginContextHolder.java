package com.example.common.context;

import com.example.common.utils.SpringUtil;

/**
 * 获取当前登录用户上下文 Holder
 *
 * @author Leo
 * @date 2020.02.19
 */
public class LoginContextHolder {
	public static LoginContext getContext() {
		return SpringUtil.getBean(LoginContext.class);
	}
}
