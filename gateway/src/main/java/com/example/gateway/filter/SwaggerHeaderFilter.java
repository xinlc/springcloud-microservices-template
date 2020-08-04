package com.example.gateway.filter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

/**
 * Swagger 过滤器添加 Header
 * <p>
 * 在 Swagger 中会根据 X-Forwarded-Prefix 这个 Header 来获取 BasePath，
 * 而 Gateway 在做转发的时候并没有这个 Header 添加进 Request，所以发生接口调试的 404 错误，
 *
 * 2020.04.22 注：
 * 	高版本 Spring Boot（>2.0.6）自动加 X-Forwarded-Prefix，不需要在请求头手工加 SwaggerHeaderFilter，
 * 	路由有 predicates 情况 SwaggerHeaderFilter 会导致的 basePath 出现逗号分割的两个服务名，去掉 SwaggerHeaderFilter 可以解决。
 * 	去掉 SwaggerHeaderFilter 后 swagger path 会丢失服务 basePath，猜测是由 StripPrefix=1 导致，可以通过 swagger  pathMapping("/order-api") 添加根 path。
 *
 * @author Leo
 * @date 2020.03.02
 */
@Component
public class SwaggerHeaderFilter extends AbstractGatewayFilterFactory<SwaggerHeaderFilter.Config> {

	private static final String HEADER_NAME = "X-Forwarded-Prefix";

	private static final String URI = "/v2/api-docs";

	public SwaggerHeaderFilter() {
		super(Config.class);
	}

	@Override
	public GatewayFilter apply(Config config) {
		return (exchange, chain) -> {
			ServerHttpRequest request = exchange.getRequest();
			String path = request.getURI().getPath();
			if (!StringUtils.endsWithIgnoreCase(path, URI)) {
				return chain.filter(exchange);
			}
			String basePath = path.substring(0, path.lastIndexOf(URI));
			ServerHttpRequest newRequest = request.mutate().header(HEADER_NAME, basePath).build();
			ServerWebExchange newExchange = exchange.mutate().request(newRequest).build();
			return chain.filter(newExchange);
		};
	}

	public static class Config {
		// Put the configuration properties for your filter here
	}
}
