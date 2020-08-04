package com.example.common.interceptor;

import com.example.common.api.ApiResultType;
import com.example.common.constant.AuthConst;
import com.example.common.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Base64Utils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 微服务防护，客户端请求资源只能通过微服务网关获取
 *
 * @author Leo
 * @date 2020.03.11
 */
public class ServerProtectInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		// 从请求头中获取 Gateway Token
		String token = request.getHeader(AuthConst.GATEWAY_TOKEN_HEADER);
		String gatewayToken = new String(Base64Utils.encode(AuthConst.GATEWAY_TOKEN_VALUE.getBytes()));

		// 校验 Gateway Token 的正确性
		if (StringUtils.equals(gatewayToken, token)) {
			return true;
		} else {
			throw new BusinessException(ApiResultType.GATEWAY_RESOURCE_DENIAL.getCode());
		}
	}
}
