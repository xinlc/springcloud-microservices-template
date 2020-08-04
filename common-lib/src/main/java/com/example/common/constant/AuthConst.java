package com.example.common.constant;

import org.springframework.http.HttpHeaders;

/**
 * 授权鉴权 常亮
 *
 * @author Leo
 * @date 2020.02.19
 */
public class AuthConst {

	public static final String COOKIE_NAME = "example";

	/**
	 * 当前用户信息请求头
	 */
	public static final String CURRENT_USER_HEADER = "example-current-user";

	/**
	 * 当前租户ID 请求头
	 */
	public static final String CURRENT_TENANT_ID_HEADER = "example-current-tenant-id";

	/**
	 * 当前系统ID 请求头
	 */
	public static final String CURRENT_SYSTEM_ID_HEADER = "example-current-system-id";

	/**
	 * HTTP 授权请求头
	 */
	public static final String X_AUTHORIZATION_HEADER = "X-Authorization";

	/**
	 * HTTP 授权请求头
	 */
	public static final String AUTHORIZATION_HEADER = HttpHeaders.AUTHORIZATION;

	/**
	 * Gateway 请求头 TOKEN 名称
	 */
	public static final String GATEWAY_TOKEN_HEADER = "gateway-token";

	/**
	 * Gateway 请求头 TOKEN 值
	 */
	public static final String GATEWAY_TOKEN_VALUE = "example:gateway:QAMD8dZwXER3bXQG";

}
