package com.example.gateway.context;

import io.swagger.annotations.Api;

/**
 * api 请求上下文 holder
 *
 * @author Leo
 * @date 2020.02.24
 */
public class ApiContextHolder {

	private static final ThreadLocal<ApiContext> contextHolder = new ThreadLocal<>();

	/**
	 * 获取上下文
	 *
	 * @return
	 */
	public static ApiContext getContext() {
		ApiContext apiContext = contextHolder.get();
		if (null == apiContext) {
			apiContext = createEmptyContext();
			contextHolder.set(apiContext);
		}
		return apiContext;
	}

	/**
	 * 设置 上下文
	 *
	 * @param context
	 */
	public static void setContext(ApiContext context) {
		contextHolder.set(context);
	}

	/**
	 * 清空上下文
	 */
	public static void clearContext() {
		contextHolder.remove();
	}

	private static ApiContext createEmptyContext() {
		return ApiContext.builder().build();
	}
}
