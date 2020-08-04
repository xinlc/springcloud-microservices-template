package com.example.auth.controller;

import com.github.pagehelper.PageInfo;
import com.example.auth.dto.req.AddRoleInfoReq;
import com.example.auth.dto.req.RolePageReq;
import com.example.auth.dto.req.UpdateRoleInfoReq;
import com.example.auth.dto.res.MenuRes;
import com.example.auth.dto.res.RoleInfoDetailRes;
import com.example.auth.dto.res.RoleInfoRes;
import com.example.auth.service.UmsRoleInfoService;
import com.example.common.annotation.CurrentUser;
import com.example.common.api.ApiResult;
import com.example.common.bo.UserInfoBo;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 角色控制器
 *
 * @author Leo
 * @since 2020.08.01
 */
@Api(value = "角色控制器", tags = "角色控制器")
@Validated
@RestController
@RequestMapping("/v1/role")
public class RoleController {

	@Autowired
	private UmsRoleInfoService umsRoleInfoService;

	@ApiOperation("角色分页列表")
	@GetMapping("/page")
	public ApiResult<List<RoleInfoRes>> getTemplatePageList(@RequestParam(defaultValue = "1") Integer page,
															@RequestParam(defaultValue = "10") Integer limit,
															@ApiIgnore @CurrentUser UserInfoBo userInfoBo,
															RolePageReq rolePageReq) {
		PageInfo<RoleInfoRes> pageInfo = umsRoleInfoService.getRoleInfoList(userInfoBo, page, limit, rolePageReq);
		return ApiResult.<List<RoleInfoRes>>SUCCESS().initSuccessInfo(pageInfo.getList(), pageInfo.getTotal());
	}

	@ApiOperation("添加角色")
	@PostMapping
	public ApiResult<Long> addRoleInfo(@Valid @RequestBody AddRoleInfoReq addRoleInfoReq,
									   @ApiIgnore @CurrentUser UserInfoBo userInfoBo) {
		Long id = umsRoleInfoService.addRoleInfo(addRoleInfoReq, userInfoBo);
		return ApiResult.<Long>SUCCESS().initSuccessInfo(id);
	}

	@ApiOperation("修改角色")
	@PutMapping
	public ApiResult<?> updateRoleInfo(@Valid @RequestBody UpdateRoleInfoReq updateRoleInfoReq,
									   @ApiIgnore @CurrentUser UserInfoBo userInfoBo) {
		umsRoleInfoService.updateRoleInfo(updateRoleInfoReq, userInfoBo);
		return ApiResult.SUCCESS();
	}

	@ApiOperation("角色详情")
	@GetMapping("/{id}")
	public ApiResult<RoleInfoDetailRes> getRoleInfoDetail(@NotNull @PathVariable Long id,
														  @ApiParam(value = "角色来源标识：1->业务角色；2->系统预设；", required = true) @NotNull @RequestParam Integer roleFlag,
														  @ApiIgnore @CurrentUser UserInfoBo userInfoBo) {
		RoleInfoDetailRes roleInfoDetailRes = umsRoleInfoService.getRoleInfoDetailById(id, roleFlag, userInfoBo);
		return ApiResult.<RoleInfoDetailRes>SUCCESS().initSuccessInfo(roleInfoDetailRes);
	}

	@ApiOperation("删除角色")
	@DeleteMapping("/{id}")
	public ApiResult<?> deleteRoleInfo(@NotNull @PathVariable Long id,
									   @ApiIgnore @CurrentUser UserInfoBo userInfoBo) {
		umsRoleInfoService.deleteRoleInfoById(id, userInfoBo);
		return ApiResult.SUCCESS();
	}

	@ApiOperation("获取系统菜单列表")
	@GetMapping("/menus")
	public ApiResult<List<MenuRes>> getCurrentSystemMenus(@ApiIgnore @CurrentUser UserInfoBo userInfoBo) {
		List<MenuRes> menuRes = umsRoleInfoService.getCurrentSystemMenus();
		return ApiResult.<List<MenuRes>>SUCCESS().initSuccessInfo(menuRes);
	}

}
