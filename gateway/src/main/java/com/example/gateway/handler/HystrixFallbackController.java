package com.example.gateway.handler;

import com.example.common.api.ApiResult;
import com.example.common.api.ApiResultType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * 响应超时熔断降级
 *
 * @author Leo
 * @date 2020.03.10
 */
@RestController
public class HystrixFallbackController {

	@RequestMapping("/defaultfallback")
	public Mono<ApiResult<?>> fallback() {
		return Mono.just(ApiResult.FAIL(ApiResultType.SYSTEM_BUSY.getCode()));
	}
}
