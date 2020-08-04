package com.example.auth.client.fallback;

import com.example.auth.client.AuthClient;
import com.example.auth.dto.res.PermissionRes;
import com.example.auth.dto.res.TenantConfigRes;
import com.example.common.api.ApiResult;
import com.example.common.api.ApiResultType;
import com.example.common.bo.UserInfoBo;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class AuthServiceFallbackImpl implements AuthClient {
	@Setter
	private Throwable cause;

	@Override
	public ApiResult<UserInfoBo> authUserInfo(String authentication, String systemId) {
		log.error("调用失败", cause);
		return ApiResult.FAIL(ApiResultType.SYS_ERROR.getCode(), cause.getMessage());
	}

	@Override
	public ApiResult<List<PermissionRes>> getCurrentUserUmsPermission() {
		log.error("调用失败", cause);
		return ApiResult.FAIL(ApiResultType.SYS_ERROR.getCode(), cause.getMessage());
	}

	@Override
	public ApiResult<TenantConfigRes> getTenantConfig(String domain) {
		log.error("调用失败", cause);
		return ApiResult.FAIL(ApiResultType.SYS_ERROR.getCode(), cause.getMessage());
	}

	@Override
	public ApiResult<Boolean> authz(String[] permissions) {
		log.error("调用失败", cause);
		return ApiResult.FAIL(ApiResultType.SYS_ERROR.getCode(), cause.getMessage());
	}
}
