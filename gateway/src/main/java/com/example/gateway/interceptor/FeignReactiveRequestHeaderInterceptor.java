package com.example.gateway.interceptor;

import com.alibaba.fastjson.JSON;
import com.example.common.constant.AuthConst;
import com.example.common.utils.OkHttpUtil;
import com.example.gateway.context.ApiContext;
import com.example.gateway.context.ApiContextHolder;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.apache.commons.lang3.StringUtils;


/**
 * WebFlux Feign 请求拦截器（设置请求头，传递请求参数）
 * <p>
 * 服务间进行 Feign 调用时，不会传递请求头信息。通过实现 RequestInterceptor 接口，完成对所有的 Feign 请求，传递请求头和请求参数。
 *
 * @author Leo
 * @date 2020.02.19
 */
public class FeignReactiveRequestHeaderInterceptor implements RequestInterceptor {
	@Override
	public void apply(RequestTemplate requestTemplate) {

		// 传递上下文中的 auth 信息
		ApiContext apiContext = ApiContextHolder.getContext();
		if (!StringUtils.isEmpty(apiContext.getTenantId())) {
			requestTemplate.header(AuthConst.CURRENT_TENANT_ID_HEADER, apiContext.getTenantId());
		}

		if (!StringUtils.isEmpty(apiContext.getSystemId())) {
			requestTemplate.header(AuthConst.CURRENT_SYSTEM_ID_HEADER, apiContext.getSystemId());
		}

		if (null != apiContext.getUserInfoBo()) {
			requestTemplate.header(AuthConst.CURRENT_USER_HEADER, OkHttpUtil.getValueEncoded(JSON.toJSONString(apiContext.getUserInfoBo())));
		}

		if (!StringUtils.isEmpty(apiContext.getAuthz())) {
			requestTemplate.header(AuthConst.AUTHORIZATION_HEADER, apiContext.getAuthz());
		}
	}
}

