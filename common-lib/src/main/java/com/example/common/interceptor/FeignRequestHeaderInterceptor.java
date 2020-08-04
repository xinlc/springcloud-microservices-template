package com.example.common.interceptor;

import com.alibaba.fastjson.JSON;
import com.example.common.context.AuthContext;
import com.example.common.bo.UserInfoBo;
import com.example.common.constant.AuthConst;
import com.example.common.utils.OkHttpUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * Feign 请求拦截器（设置请求头，传递请求参数）
 * <p>
 * 服务间进行 Feign 调用时，不会传递请求头信息。通过实现 RequestInterceptor 接口，完成对所有的 Feign 请求，传递请求头和请求参数。
 *
 * @author Leo
 * @date 2020.02.19
 */
public class FeignRequestHeaderInterceptor implements RequestInterceptor {

	@Override
	public void apply(RequestTemplate requestTemplate) {

		// Feign 请求拦截器（设置请求头，传递请求参数）
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		if (null != attributes) {
			HttpServletRequest request = attributes.getRequest();
			Enumeration<String> headerNames = request.getHeaderNames();
			if (headerNames != null) {
				while (headerNames.hasMoreElements()) {
					String name = headerNames.nextElement();
					String values = request.getHeader(name);
					requestTemplate.header(name, values);
				}
			}
		}

		// 传递上下文中的用户信息
		UserInfoBo userInfoBo = AuthContext.getUserInfoBo();
		if (null != userInfoBo) {
			requestTemplate.header(AuthConst.CURRENT_USER_HEADER, OkHttpUtil.getValueEncoded(JSON.toJSONString(userInfoBo)));
		}
	}
}
