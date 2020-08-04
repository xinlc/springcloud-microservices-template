package com.example.common.interceptor;

import com.example.common.context.AuthContext;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * API 拦截器
 *
 * @author Leo
 * @date 2020.07.23
 */
public class ApiInterceptor extends HandlerInterceptorAdapter {

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		// 清空上下文，避免线程池共享
		AuthContext.clearContext();
	}
}
