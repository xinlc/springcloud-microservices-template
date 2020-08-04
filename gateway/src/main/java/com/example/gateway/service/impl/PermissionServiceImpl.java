package com.example.gateway.service.impl;

import com.alibaba.fastjson.JSON;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.example.auth.client.AuthClient;
import com.example.auth.constant.AuthConstant;
import com.example.auth.dto.res.PermissionRes;
import com.example.auth.dto.res.TenantConfigRes;
import com.example.common.api.ApiResult;
import com.example.common.api.ApiResultType;
import com.example.common.bo.UserInfoBo;
import com.example.common.constant.AuthConst;
import com.example.common.exception.BusinessException;
import com.example.common.redis.RedisService;
import com.example.common.utils.OkHttpUtil;
import com.example.gateway.config.IgnoreUrlsProps;
import com.example.gateway.context.ApiContext;
import com.example.gateway.provider.AuthProvider;
import com.example.gateway.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class PermissionServiceImpl implements PermissionService {

	@Autowired
	private IgnoreUrlsProps ignoreUrlsProps;

	@Autowired
	private RedisService redisService;

	@Autowired
	@Qualifier(AuthConstant.SERVICE_NAME)
	private AuthClient authClient;

	@Autowired
	private AuthProvider authProvider;

	@Lazy
	@Autowired
	private PermissionService permissionService;

	@Cached(name = "gateway_auth.getTenantConfig.", key = "#domain", expire = 120, cacheType = CacheType.BOTH)
	@Override
	public TenantConfigRes getTenantConfig(String domain) {
		ApiResult<TenantConfigRes> apiResult = authClient.getTenantConfig(domain);
		if (!apiResult.isSuccess()) {
			log.error("获取租户配置失败");
		}
		TenantConfigRes tenantConfigRes = apiResult.getData();
		return tenantConfigRes;
	}

	@Override
	public Mono<TenantConfigRes> getTenantConfigAsync(String domain) {
		return Mono.fromCallable(() -> permissionService.getTenantConfig(domain))
				.defaultIfEmpty(new TenantConfigRes())
				.subscribeOn(Schedulers.elastic());
	}

	@Cached(name = "gateway_auth.ignoreAuthentication.", key = "#url",
			cacheType = CacheType.LOCAL, expire = 60, localLimit = 1000)
	@Override
	public boolean ignoreAuthentication(String url) {
		AntPathMatcher antPathMatcher = new AntPathMatcher();
		antPathMatcher.setCachePatterns(true);
		antPathMatcher.setCaseSensitive(true);
		antPathMatcher.setTrimTokens(true);
		antPathMatcher.setPathSeparator("/");
		return ignoreUrlsProps.getUrls()
				.stream()
				.anyMatch(u -> antPathMatcher.match(u, url));
	}

	@Cached(name = "gateway_auth.authUserInfo.", key = "#authentication",
			cacheType = CacheType.LOCAL, expire = 10, localLimit = 1000)
	@Override
	public UserInfoBo authUserInfo(ApiContext apiContext, String authentication) {

		// 判定登录令牌是否存在
		if (StringUtils.isBlank(authentication)) {
			throw new BusinessException(ApiResultType.LOGON_FILE.getCode());
		}

		// 鉴权，并返回用户信息
		ApiResult<UserInfoBo> result = authClient.authUserInfo(authentication, apiContext.getSystemId());
		if (result.isSuccess()) {
			return result.getData();
		}

		// Token 失效
		throw new BusinessException(ApiResultType.LOGON_FILE.getCode());
	}

	@Override
	public Mono<UserInfoBo> authUserInfoAsync(ApiContext apiContext, String authentication) {
		return Mono.fromCallable(() -> permissionService.authUserInfo(apiContext, authentication))
				.defaultIfEmpty(new UserInfoBo())
				.subscribeOn(Schedulers.elastic());
	}

	@Cached(name = "gateway_auth.permission.", key = "#authentication+'-'+#method+'-'+#url",
			cacheType = CacheType.LOCAL, expire = 60, timeUnit = TimeUnit.SECONDS, localLimit = 10000)
	@Override
	public boolean permission(ApiContext apiContext, TenantConfigRes tenantConfigRes, String authentication, String url, String method) throws InterruptedException {
		String tenantId = tenantConfigRes.getTenantId();

		// 校验租户是否有效
		LocalDateTime expirationTime = tenantConfigRes.getExpirationTime();
		if (expirationTime.isBefore(LocalDateTime.now())) {
			log.info("租户：{} 已失效", tenantId);
			return false;
		}

		// 获取权限列表
		ApiResult<List<PermissionRes>> apiResult = authProvider.getCurrentUserUmsPermission(
				apiContext.getTenantId(),
				apiContext.getSystemId(),
				OkHttpUtil.getValueEncoded(JSON.toJSONString(apiContext.getUserInfoBo()))
		);
		List<PermissionRes> permissionRes = apiResult.getData();

		AntPathMatcher antPathMatcher = new AntPathMatcher();
		antPathMatcher.setCachePatterns(true);
		antPathMatcher.setCaseSensitive(true);
		antPathMatcher.setTrimTokens(true);
		antPathMatcher.setPathSeparator("/");

		// 校验 API 接口权限
		log.debug("permissionRes list : {}", permissionRes);
		return Optional.ofNullable(permissionRes)
				.orElse(new ArrayList<>())
				.stream()
				.anyMatch(p -> {
					if (method.equals(p.getApiMethod())) {
						String apiPath = "/" + p.getApiPrefix() + p.getApiPath();
						if (antPathMatcher.match(apiPath, url)) {
							return true;
						}
					}
					return false;
				});
	}

	@Override
	public Mono<Boolean> permissionAsync(ApiContext apiContext, TenantConfigRes tenantConfigRes, String authentication, String url, String method) {
		return Mono.fromCallable(() -> permissionService.permission(apiContext, tenantConfigRes, authentication, url, method)).subscribeOn(Schedulers.elastic());
	}
}

