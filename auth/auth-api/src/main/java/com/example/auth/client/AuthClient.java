package com.example.auth.client;

import com.example.auth.client.factory.AuthServiceFallbackFactory;
import com.example.auth.constant.AuthConstant;
import com.example.auth.dto.res.PermissionRes;
import com.example.auth.dto.res.TenantConfigRes;
import com.example.common.api.ApiResult;
import com.example.common.bo.UserInfoBo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 授权，鉴权 客户端
 */
@FeignClient(name = AuthConstant.SERVICE_NAME, qualifier = AuthConstant.SERVICE_NAME, path = "/v1/auth", url = "${example.auth-service-endpoint}", fallbackFactory = AuthServiceFallbackFactory.class)
// 暂时不启用客户端参数校验
// @Validated
public interface AuthClient {

	@GetMapping("/auth/userInfo")
	ApiResult<UserInfoBo> authUserInfo(@RequestParam("authentication") String authentication, @RequestParam("systemId") String systemId);

	@GetMapping(value = "/permission")
	ApiResult<List<PermissionRes>> getCurrentUserUmsPermission();

	@GetMapping("/tenant/config")
	ApiResult<TenantConfigRes> getTenantConfig(@RequestParam("domain") String domain);

	@PostMapping("/authz")
	ApiResult<Boolean> authz(@RequestBody String[] permissions);

}

