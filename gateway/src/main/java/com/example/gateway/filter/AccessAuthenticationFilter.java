package com.example.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.auth.dto.res.TenantConfigRes;
import com.example.common.api.ApiResult;
import com.example.common.api.ApiResultType;
import com.example.common.bo.UserInfoBo;
import com.example.common.constant.AuthConst;
import com.example.common.utils.OkHttpUtil;
import com.example.gateway.config.TerminalConfigProps;
import com.example.gateway.config.TerminalProps;
import com.example.gateway.context.ApiContext;
import com.example.gateway.service.PermissionService;
import com.example.gateway.utils.AccessUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 全局请求认证，鉴权，权限校验
 *
 * @author Leo
 * @date 2020.02.14
 */
@Configuration
@Slf4j
public class AccessAuthenticationFilter implements GlobalFilter, Ordered {

	public static final int ACCESS_AUTH_FILTER_ORDER = 0;

	@Autowired
	private TerminalConfigProps terminalConfigProps;

	@Autowired
	private PermissionService permissionService;

	/**
	 * 1. 获取必要的基本信息，比如：租户信息、系统信息、用户信息等。
	 * 2. 认证用户信息，检查是否有效，有效返回用户信息和最新的 token, 无效直接抛出业务异常。
	 * 3. 获取权限信息，检查是否有权限，未授权返回 401。
	 * 4. 有权限，封装租户信息，系统信息，用户信息到 Header 中供后面的服务使用。
	 * 5. token 存入 cookie。
	 *
	 * @param exchange
	 * @param chain
	 * @return
	 */
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		ServerHttpRequest request = exchange.getRequest();
		String method = request.getMethodValue();
		String url = request.getPath().value();

		// 获取授权信息 (JWT)
		String authentication = request.getHeaders().getFirst(AuthConst.AUTHORIZATION_HEADER);
		if (StringUtils.isEmpty(authentication)) {
			// 兼容旧请求头 X-Authorization
			String xAuthentication = request.getHeaders().getFirst(AuthConst.X_AUTHORIZATION_HEADER);
			if (!StringUtils.isEmpty(xAuthentication)) {
				authentication = "Bearer " + xAuthentication;
			}
		}
		String finalAuthentication = authentication;

		// 获取系统 ID
		TerminalProps terminalProps = AccessUtil.getTerminalConfig(request, terminalConfigProps);
		if (StringUtils.isBlank(terminalProps.getSystemId())) {
			log.warn("未获取到系统ID，请求URI: {}", url);
		}

		// 获取租户配置
		Mono<TenantConfigRes> tenantConfigResAsync = permissionService.getTenantConfigAsync(AccessUtil.getDomain(request));
		AtomicReference<TenantConfigRes> tenantConfigRes = new AtomicReference<>();

		// 存储上下文
		ApiContext apiContext = ApiContext.builder()
				.systemId(terminalProps.getSystemId())
				.build();

		return tenantConfigResAsync
				.flatMap(t -> {
					apiContext.setTenantId(t.getTenantId());
					tenantConfigRes.set(t);

					// 穿透 header
					ServerHttpRequest.Builder builder = request.mutate();
					builder.header(AuthConst.CURRENT_TENANT_ID_HEADER, t.getTenantId());
					builder.header(AuthConst.CURRENT_SYSTEM_ID_HEADER, terminalProps.getSystemId());

					// 不需要网关签权的 url
					if (permissionService.ignoreAuthentication(url)) {
						log.debug("忽略鉴权：{}", url);
						return chain.filter(exchange.mutate().request(builder.build()).build());
					}
					return Mono.just(Optional.empty());
				})
				.flatMap(o -> Mono.just(Optional.of(o)))
				// 不需要鉴权，抛出空，完成信号，不执行后面 flatMap
				.defaultIfEmpty(Optional.empty())
				// 认证用户信息
				.flatMap(o -> {
					if (o.isPresent()) {
						return permissionService.authUserInfoAsync(apiContext, finalAuthentication);
					}
					return Mono.empty();
				})
				.doOnNext(o -> log.debug("获取用户信息：{}", o))
				// 是否登录
				.flatMap(userInfoBo -> {
					if (null == userInfoBo.getUserId()) {
						log.warn("用户未登录，或登录已失效");
						return Mono.just(false);
					}
					apiContext.setUserInfoBo(userInfoBo);
					return permissionService.permissionAsync(apiContext, tenantConfigRes.get(), finalAuthentication, url, method);
				})
				.flatMap(hasPermission -> {
					if (hasPermission) {
						log.debug("有权限访问：{}", url);
						// 穿透 header
						ServerHttpRequest.Builder builder = request.mutate();
						builder.header(AuthConst.CURRENT_TENANT_ID_HEADER, apiContext.getTenantId());
						builder.header(AuthConst.CURRENT_SYSTEM_ID_HEADER, terminalProps.getSystemId());
						// 将 jwt token 中的用户信息传给服务
//						builder.header(AuthConstant.CURRENT_USER_HEADER, getUserToken(authentication));
						builder.header(AuthConst.CURRENT_USER_HEADER, OkHttpUtil.getValueEncoded(JSON.toJSONString(apiContext.getUserInfoBo())));

						// 获取 token 存入 cookie
						String token = apiContext.getUserInfoBo().getToken();
						ResponseCookie responseCookie = ResponseCookie.from(terminalProps.getCookieTokenKey(), token)
								.path("/")
								.build();
						exchange.getResponse().addCookie(responseCookie);

						return chain.filter(exchange.mutate().request(builder.build()).build());
					}
					log.warn("未授权访问：{}", url);
					return unauthorized(exchange);
				});

	}

	/**
	 * 验证 token
	 *
	 * @return
	 */
	private boolean tokenAuthentication(UserInfoBo userInfoBo, String authentication) {
		if (null != userInfoBo && null != userInfoBo.getUserId()) {
			return true;
		}
		return false;
	}

	/**
	 * 提取 jwt token中的数据，转为json
	 *
	 * @param authentication
	 * @return
	 */
	private String getUserToken(String authentication) {
		String token = "{}";
//		try {
//			token = new ObjectMapper().writeValueAsString(permissionService.getJwt(authentication).getBody());
//			return token;
//		} catch (JsonProcessingException e) {
//			log.error("token json error:{}", e.getMessage());
//		}
		return token;
	}

	/**
	 * 网关拒绝，返回 401
	 */
	private Mono<Void> unauthorized(ServerWebExchange serverWebExchange) {
		ServerHttpResponse response = serverWebExchange.getResponse();
		response.setStatusCode(HttpStatus.UNAUTHORIZED);
		response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

		// 构建返回值 JSON
		ApiResult<?> apiResult = ApiResult.FAIL(ApiResultType.GATEWAY_UNAUTHORIZED.getCode());
		String result = "";
		try {
			result = new ObjectMapper().writeValueAsString(apiResult);
		} catch (JsonProcessingException e) {
			log.error(e.getMessage(), e);
		}

		DataBuffer buffer = response.bufferFactory().wrap(result.getBytes(StandardCharsets.UTF_8));
		return serverWebExchange.getResponse().writeWith(Flux.just(buffer));
	}

	@Override
	public int getOrder() {
		return ACCESS_AUTH_FILTER_ORDER;
	}
}
