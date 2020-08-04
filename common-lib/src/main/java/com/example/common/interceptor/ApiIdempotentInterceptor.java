package com.example.common.interceptor;

import com.example.common.annotation.ApiIdempotent;
import com.example.common.redis.RedisTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 接口幂等性拦截器
 *
 * @author Leo
 * @date 2020.04.29
 */
public class ApiIdempotentInterceptor implements HandlerInterceptor {

	@Autowired
	private RedisTokenService tokenService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		if (!(handler instanceof HandlerMethod)) {
			return true;
		}

		HandlerMethod handlerMethod = (HandlerMethod) handler;
		Method method = handlerMethod.getMethod();

		ApiIdempotent methodAnnotation = method.getAnnotation(ApiIdempotent.class);
		if (null != methodAnnotation) {
			// 幂等性校验, 校验通过则放行, 校验失败则抛出异常
			check(request);
		}

		return true;
	}

	private void check(HttpServletRequest request) {
		tokenService.checkToken(request);
	}

	@Override
	public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, Exception ex) throws Exception {
	}
}
