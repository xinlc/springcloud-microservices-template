package com.example.auth.controller;

import com.example.auth.dto.res.MenuRes;
import com.example.auth.dto.res.PermissionRes;
import com.example.auth.dto.res.TenantConfigRes;
import com.example.auth.service.UmsPermissionService;
import com.example.common.annotation.CurrentUser;
import com.example.common.api.ApiResult;
import com.example.common.bo.UserInfoBo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * 权限控制器
 *
 * @author Leo
 * @since 2020.08.01
 */
@Api(value = "权限控制器", tags = "权限控制器")
@Validated
@RestController
@RequestMapping("/v1/auth")
public class PermissionController {

	@Autowired
	private UmsPermissionService umsPermissionService;

	@ApiOperation("获取租户配置 for gateway")
	@GetMapping("/tenant/config")
	public ApiResult<TenantConfigRes> getTenantConfig(@RequestParam String domain) {

		TenantConfigRes tenantConfigRes = umsPermissionService.getTenantConfigByDomain(domain);
		return ApiResult.<TenantConfigRes>SUCCESS().initSuccessInfo(tenantConfigRes);
	}

	@ApiOperation("鉴权")
	@GetMapping("/auth/userInfo")
	public ApiResult<UserInfoBo> authUserInfo(@RequestParam String authentication, @RequestParam String systemId) {
		UserInfoBo userInfoBo = umsPermissionService.authUserInfo(authentication, systemId);
		return ApiResult.<UserInfoBo>SUCCESS().initSuccessInfo(userInfoBo);
	}

	@ApiOperation("获取当前登录人的所有菜单")
	@GetMapping("/menus")
	public ApiResult<List<MenuRes>> getCurrentUserMenusList(@ApiIgnore @CurrentUser UserInfoBo userInfoBo) {
		List<MenuRes> menuRes = umsPermissionService.getCurrentUserMenusList(userInfoBo);
		return ApiResult.<List<MenuRes>>SUCCESS().initSuccessInfo(menuRes);
	}

	@ApiOperation("获取当前登录人的所有权限")
	@GetMapping("/permission")
	public ApiResult<List<PermissionRes>> getCurrentUserUmsPermission(@ApiIgnore @CurrentUser UserInfoBo userInfoBo) {
		List<PermissionRes> permissionRes = umsPermissionService.getCurrentUserUmsPermission(userInfoBo);
		return ApiResult.<List<PermissionRes>>SUCCESS().initSuccessInfo(permissionRes);
	}

	@ApiOperation("鉴权")
	@PostMapping("/authz")
	public ApiResult<Boolean> authz(@RequestBody String[] permissions,
									@ApiIgnore @CurrentUser UserInfoBo userInfoBo) {
		Boolean isPermitted = umsPermissionService.authz(permissions, userInfoBo);
		return ApiResult.<Boolean>SUCCESS().initSuccessInfo(isPermitted);
	}


}
