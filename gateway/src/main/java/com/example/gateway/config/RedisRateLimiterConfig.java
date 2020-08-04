package com.example.gateway.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import reactor.core.publisher.Mono;

/**
 * 网关路由限流配置
 *
 * @author Leo
 * @date 2020.03.10
 */
@Configuration
public class RedisRateLimiterConfig {

	/**
	 * 基于用户ID限流
	 *
	 * @return
	 */
	@Bean(value = "userKeyResolver")
	KeyResolver userKeyResolver() {
		return exchange -> Mono.just(exchange.getRequest().getQueryParams().getFirst("userId"));
	}

	/**
	 * 基于IP 限流
	 *
	 * @return
	 */
	@Bean(value = "ipKeyResolver")
	public KeyResolver ipKeyResolver() {
		return exchange -> Mono.just(exchange.getRequest().getRemoteAddress().getAddress().getHostAddress());
	}


	/**
	 * 根据 HostName 进行限流
	 *
	 * @return
	 */
	@Primary
	@Bean("hostNameKeyResolver")
	public KeyResolver hostNameKeyResolver() {
		return exchange -> Mono.just(exchange.getRequest().getRemoteAddress().getHostName());
	}

	/**
	 * 根据api接口来限流
	 *
	 * @return
	 */
	@Bean(name = "apiKeyResolver")
	public KeyResolver apiKeyResolver() {
		return exchange -> Mono.just(exchange.getRequest().getPath().value());
	}
}
