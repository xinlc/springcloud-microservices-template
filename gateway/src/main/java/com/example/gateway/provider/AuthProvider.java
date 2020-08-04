package com.example.gateway.provider;

import com.example.auth.dto.res.PermissionRes;
import com.example.common.api.ApiResult;
import com.example.common.constant.AuthConst;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

/**
 * 授权，鉴权 客户端
 */
//@FeignClient(name = "auth-service", path = "/v1/auth")
@FeignClient(name = "auth-provider", path = "/v1/auth", url = "${example.auth-service-endpoint}")
//@FeignClient(name = "auth-service", path = "/v1/auth", fallback = AuthClient.AuthFallback.class)
//@FeignClient(name = AuthConstant.SERVICE_NAME, path = "/v1/auth", url = "${example.auth-service-endpoint}")

// 暂时不启用客户端参数校验
// @Validated
public interface AuthProvider {

	@GetMapping(value = "/permission")
	ApiResult<List<PermissionRes>> getCurrentUserUmsPermission(
			@RequestHeader(AuthConst.CURRENT_TENANT_ID_HEADER) String tid,
			@RequestHeader(AuthConst.CURRENT_SYSTEM_ID_HEADER) String sid,
			@RequestHeader(AuthConst.CURRENT_USER_HEADER) String user
	);
}

