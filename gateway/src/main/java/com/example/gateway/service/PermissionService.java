package com.example.gateway.service;

import com.example.auth.dto.res.TenantConfigRes;
import com.example.common.bo.UserInfoBo;
import com.example.gateway.context.ApiContext;
import reactor.core.publisher.Mono;

/**
 * 权限认证
 *
 * @author Leo
 * @date 2020.02.14
 */
public interface PermissionService {

	/**
	 * 获取租户配置
	 *
	 * @param domain
	 * @return
	 */
	TenantConfigRes getTenantConfig(String domain);

	Mono<TenantConfigRes> getTenantConfigAsync(String domain);

	/**
	 * 验证是否是不需要鉴权的 api
	 *
	 * @param url
	 * @return
	 */
	boolean ignoreAuthentication(String url);

	/**
	 * 认证用户信息
	 *
	 * @param apiContext apiContext
	 * @param authentication
	 * @return
	 */
	UserInfoBo authUserInfo(ApiContext apiContext, String authentication);

	Mono<UserInfoBo> authUserInfoAsync(ApiContext apiContext, String authentication);

	/**
	 * 权限认证
	 *
	 * @param tenantConfigRes
	 * @param authentication
	 * @param method
	 * @param url
	 * @return
	 */
	boolean permission(ApiContext apiContext, TenantConfigRes tenantConfigRes, String authentication, String url, String method) throws InterruptedException;

	Mono<Boolean> permissionAsync(ApiContext apiContext, TenantConfigRes tenantConfigRes, String authentication, String url, String method);

}
