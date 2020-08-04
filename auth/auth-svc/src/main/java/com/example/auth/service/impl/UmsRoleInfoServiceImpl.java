package com.example.auth.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.constant.BusinessConst;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.example.auth.dto.req.AddRoleInfoReq;
import com.example.auth.dto.req.RolePageReq;
import com.example.auth.dto.req.UpdateRoleInfoReq;
import com.example.auth.dto.res.MenuRes;
import com.example.auth.dto.res.RoleInfoDetailRes;
import com.example.auth.dto.res.RoleInfoRes;
import com.example.auth.emum.RoleTypeEnum;
import com.example.auth.service.UmsRoleInfoService;
import com.example.auth.service.UmsRoleMenuService;
import com.example.common.api.ApiResultType;
import com.example.common.context.AuthContext;
import com.example.common.exception.BusinessException;
import com.example.common.bo.UserInfoBo;
import com.example.dao.entity.*;
import com.example.dao.mapper.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色表 服务实现类
 *
 * @author Leo
 * @since 2020.08.01
 */
@Service
public class UmsRoleInfoServiceImpl extends ServiceImpl<UmsRoleInfoMapper, UmsRoleInfo> implements UmsRoleInfoService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	public UmsRoleInfoMapper umsRoleInfoMapper;

	@Autowired
	public UmsRoleInfoSystemMapper umsRoleInfoSystemMapper;

	@Autowired
	public UmsRoleMenuService umsRoleMenuService;

	@Autowired
	public UmsRoleMenuMapper umsRoleMenuMapper;

	@Autowired
	public UmsRoleMenuSystemMapper umsRoleMenuSystemMapper;

	@Autowired
	public UmsMenuMapper umsMenuMapper;

	@Autowired
	public TenantConfigMapper tenantConfigMapper;

	@Autowired
	public BizPlanMapper bizPlanMapper;

	@Autowired
	public BizPlanResourceMapper bizPlanResourceMapper;

	@Autowired
	private UmsUserRoleMapper umsUserRoleMapper;

	@Override
	public PageInfo<RoleInfoRes> getRoleInfoList(UserInfoBo userInfoBo, Integer page, Integer limit, RolePageReq rolePageReq) {

		// 获取业务角色
		PageHelper.startPage(page, limit);
		List<UmsRoleInfo> umsRoleInfos = umsRoleInfoMapper.selectList(Wrappers.<UmsRoleInfo>lambdaQuery()
				.eq(UmsRoleInfo::getOrgId, userInfoBo.getOrgId())
				.eq(UmsRoleInfo::getOrgType, userInfoBo.getOrgType())
				.eq(UmsRoleInfo::getDeleteFlag, 0)
				.eq(UmsRoleInfo::getRoleType, RoleTypeEnum.NORMAL_ROLE.getValue())
				.eq(rolePageReq.getRoleStatus() != null, UmsRoleInfo::getRoleStatus, rolePageReq.getRoleStatus())
		);

		// 获取 page info
		PageInfo<UmsRoleInfo> pageInfo = new PageInfo<>(umsRoleInfos);
		PageInfo<RoleInfoRes> pageInfos = modelMapper.map(pageInfo, new TypeToken<PageInfo<RoleInfoRes>>() {
		}.getType());

		// 映射 RoleInfoRes
		List<RoleInfoRes> roleInfos = modelMapper.map(pageInfos.getList(), new TypeToken<List<RoleInfoRes>>() {
		}.getType());
		pageInfos.setList(roleInfos);

		// 获取系统角色
		if (page == 1) {
			Long systemId = Long.valueOf(AuthContext.getSystemId());
			List<UmsRoleInfoSystem> umsRoleInfoSystems = umsRoleInfoSystemMapper.selectList(Wrappers.<UmsRoleInfoSystem>lambdaQuery()
					.eq(UmsRoleInfoSystem::getSystemId, systemId)
					.eq(UmsRoleInfoSystem::getDeleteFlag, 0));

			if (umsRoleInfoSystems.size() > 0) {
				List<RoleInfoRes> sysRoleInfos = modelMapper.map(umsRoleInfoSystems, new TypeToken<List<RoleInfoRes>>() {
				}.getType());

				// 设置默认值
				sysRoleInfos.forEach(i -> {
					i.setRoleStatus(1);
					i.setRoleFlag(BusinessConst.SYSTEM_ROLE_FLAG);
				});

				// 合并到 pageInfo
				pageInfos.getList().addAll(0, sysRoleInfos);
				pageInfos.setTotal(pageInfos.getTotal() + sysRoleInfos.size());
			}
		}

		return pageInfos;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Long addRoleInfo(AddRoleInfoReq addRoleInfoReq, UserInfoBo userInfoBo) {
		// 校验是否能添加传递过来的权限id
		verifyAddRoleInfo(addRoleInfoReq, userInfoBo);

		// 保存角色
		UmsRoleInfo umsRoleInfo = modelMapper.map(addRoleInfoReq, UmsRoleInfo.class);
		umsRoleInfo.setOrgId(userInfoBo.getOrgId());
		umsRoleInfo.setOrgType(userInfoBo.getOrgType());
		umsRoleInfo.setCreateUserId(userInfoBo.getUserId());
		umsRoleInfo.setUpdateUserId(userInfoBo.getUserId());
		umsRoleInfo.insert();

		Long roleId = umsRoleInfo.getId();

		// 保存角色和菜单关联
		List<UmsRoleMenu> umsRoleMenus = new ArrayList<>();
		addRoleInfoReq.getPermissionIds().forEach(menuId -> {
			UmsRoleMenu umsRoleMenu = new UmsRoleMenu();
			umsRoleMenu.setMenuId(menuId);
			umsRoleMenu.setRoleId(roleId);
			umsRoleMenu.setCreateUserId(userInfoBo.getUserId());
			umsRoleMenu.setUpdateUserId(userInfoBo.getUserId());
			umsRoleMenus.add(umsRoleMenu);
		});
		umsRoleMenuService.saveBatch(umsRoleMenus);

		return roleId;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void updateRoleInfo(UpdateRoleInfoReq updateRoleInfoReq, UserInfoBo userInfoBo) {
		// 校验是否能添加传递过来的权限id
		verifyUpdateRoleInfo(updateRoleInfoReq, userInfoBo);

		// 修改角色
		UmsRoleInfo umsRoleInfo = modelMapper.map(updateRoleInfoReq, UmsRoleInfo.class);
		umsRoleInfo.setUpdateUserId(userInfoBo.getUserId());
		int updateRows = umsRoleInfoMapper.update(
				umsRoleInfo,
				Wrappers.<UmsRoleInfo>lambdaUpdate()
						.eq(UmsRoleInfo::getId, updateRoleInfoReq.getId())
						.eq(UmsRoleInfo::getOrgId, userInfoBo.getOrgId())
						.eq(UmsRoleInfo::getRoleType, RoleTypeEnum.NORMAL_ROLE.getValue())
		);

		// 操作异常，打回请求，避免子项更新异常。
		if (updateRows == 0) {
			throw new BusinessException(ApiResultType.ROLE_INFO_INVALID_OPERATION.getCode());
		}

		Long roleId = umsRoleInfo.getId();

		// 删除权限
		umsRoleMenuMapper.update(null, Wrappers.<UmsRoleMenu>lambdaUpdate()
				.set(UmsRoleMenu::getDeleteFlag, null)
				.set(UmsRoleMenu::getUpdateUserId, userInfoBo.getUserId())
				.eq(UmsRoleMenu::getRoleId, roleId));

		// 保存角色和菜单关联
		List<UmsRoleMenu> umsRoleMenus = new ArrayList<>();
		updateRoleInfoReq.getPermissionIds().forEach(menuId -> {
			UmsRoleMenu umsRoleMenu = new UmsRoleMenu();
			umsRoleMenu.setMenuId(menuId);
			umsRoleMenu.setRoleId(roleId);
			umsRoleMenu.setCreateUserId(userInfoBo.getUserId());
			umsRoleMenu.setUpdateUserId(userInfoBo.getUserId());
			umsRoleMenus.add(umsRoleMenu);
		});
		umsRoleMenuService.saveBatch(umsRoleMenus);

	}

	@Override
	public RoleInfoDetailRes getRoleInfoDetailById(Long id, Integer roleFlag, UserInfoBo userInfoBo) {
		RoleInfoDetailRes roleInfoDetailRes;

		List<Long> menuIds = new ArrayList<>();
		// 获取业务角色菜单
		if (BusinessConst.BUSINESS_ROLE_FLAG.equals(roleFlag)) {
			// 获取角色信息
			UmsRoleInfo umsRoleInfo = umsRoleInfoMapper.selectOne(Wrappers.<UmsRoleInfo>lambdaQuery()
					.eq(UmsRoleInfo::getId, id)
					.eq(UmsRoleInfo::getOrgId, userInfoBo.getOrgId())
					.eq(UmsRoleInfo::getRoleType, RoleTypeEnum.NORMAL_ROLE.getValue())
			);

			roleInfoDetailRes = modelMapper.map(umsRoleInfo, RoleInfoDetailRes.class);

			// 获取角色和菜单关联
			menuIds = umsRoleMenuMapper.selectList(Wrappers.<UmsRoleMenu>lambdaQuery()
					.eq(UmsRoleMenu::getRoleId,  umsRoleInfo.getId())
					.eq(UmsRoleMenu::getDeleteFlag, 0))
					.stream()
					.map(UmsRoleMenu::getMenuId)
					.collect(Collectors.toList());
		} else if (BusinessConst.SYSTEM_ROLE_FLAG.equals(roleFlag)) {
			// 获取系统角色菜单
			UmsRoleInfoSystem umsRoleInfoSystem = umsRoleInfoSystemMapper.selectOne(Wrappers.<UmsRoleInfoSystem>lambdaQuery()
					.eq(UmsRoleInfoSystem::getId, id));

			roleInfoDetailRes = modelMapper.map(umsRoleInfoSystem, RoleInfoDetailRes.class);
			roleInfoDetailRes.setRoleFlag(BusinessConst.SYSTEM_ROLE_FLAG);

			// 获取角色和菜单关联
			menuIds = umsRoleMenuSystemMapper.selectList(Wrappers.<UmsRoleMenuSystem>lambdaQuery()
					.eq(UmsRoleMenuSystem::getRoleId, umsRoleInfoSystem.getId())
					.eq(UmsRoleMenuSystem::getDeleteFlag, 0))
					.stream()
					.map(UmsRoleMenuSystem::getMenuId)
					.collect(Collectors.toList());
		} else {
			throw new BusinessException(ApiResultType.ROLE_NOT_EXISTS.getCode());
		}

		// 获取菜单
		List<UmsMenu> umsMenus = null;
		if (null != menuIds && menuIds.size() > 0) {
			umsMenus = umsMenuMapper.selectList(Wrappers.<UmsMenu>lambdaQuery()
					.in(UmsMenu::getId, menuIds)
					.orderByAsc(UmsMenu::getSortOrder));
		}

		if (null != umsMenus) {
			List<MenuRes> menuRes = modelMapper.map(umsMenus, new TypeToken<List<UmsMenu>>() {
			}.getType());

			roleInfoDetailRes.setMenuList(menuRes);
		}

		return roleInfoDetailRes;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void deleteRoleInfoById(Long id, UserInfoBo userInfoBo) {
		// 校验角色是否有关联用户
		List<UmsUserRole> umsUserRoleList = umsUserRoleMapper.selectList(Wrappers.<UmsUserRole>lambdaQuery()
				.eq(UmsUserRole::getRoleId, id)
				.eq(UmsUserRole::getRoleFlag, BusinessConst.BUSINESS_ROLE_FLAG)
				.eq(UmsUserRole::getDeleteFlag, 0));
		if (umsUserRoleList != null && umsUserRoleList.size() > 0) {
			throw new BusinessException(ApiResultType.CANNOT_DELETE_INUSE_ROLE.getCode());
		}

		umsRoleInfoMapper.update(null, Wrappers.<UmsRoleInfo>lambdaUpdate()
				.set(UmsRoleInfo::getDeleteFlag, null)
				.set(UmsRoleInfo::getUpdateUserId, userInfoBo.getUserId())
				.eq(UmsRoleInfo::getId, id)
				.eq(UmsRoleInfo::getOrgId, userInfoBo.getOrgId())
				.eq(UmsRoleInfo::getRoleType, RoleTypeEnum.NORMAL_ROLE.getValue())
		);
	}

	@Override
	public List<MenuRes> getCurrentSystemMenus() {
		Long systemId = Long.valueOf(AuthContext.getSystemId());
		Long tenantId = Long.valueOf(AuthContext.getTenantId());

		// 获取租户购买的方案
		TenantConfig tenantConfig = tenantConfigMapper.selectOne(Wrappers.<TenantConfig>lambdaQuery()
				.eq(TenantConfig::getTenantId, tenantId)
				.last("LIMIT 1"));
		List<Long> resourceIds = bizPlanResourceMapper.selectList(Wrappers.<BizPlanResource>lambdaQuery()
				.eq(BizPlanResource::getPlanId, tenantConfig.getPlanId()))
				.stream()
				.map(BizPlanResource::getResourceId)
				.collect(Collectors.toList());

		// 获取菜单
		List<UmsMenu> umsMenus = umsMenuMapper.selectList(Wrappers.<UmsMenu>lambdaQuery()
				.eq(UmsMenu::getSystemId, systemId)
				.in(UmsMenu::getResourceId, resourceIds)
				.orderByAsc(UmsMenu::getSortOrder));

		List<MenuRes> menuRes = modelMapper.map(umsMenus, new TypeToken<List<MenuRes>>() {
		}.getType());

		return menuRes;
	}

	/**
	 * 校验是否能添加传递过来的权限id
	 *
	 * @param addRoleInfoReq
	 * @param userInfoBo
	 */
	private void verifyAddRoleInfo(AddRoleInfoReq addRoleInfoReq, UserInfoBo userInfoBo) {
		verifyAddRolePermission(addRoleInfoReq.getPermissionIds(), userInfoBo);
	}

	/**
	 * 校验是否能添加传递过来的权限id
	 *
	 * @param updateRoleInfoReq
	 * @param userInfoBo
	 */
	private void verifyUpdateRoleInfo(UpdateRoleInfoReq updateRoleInfoReq, UserInfoBo userInfoBo) {
		verifyAddRolePermission(updateRoleInfoReq.getPermissionIds(), userInfoBo);
	}

	/**
	 * 校验是否能操作传递过来的权限id
	 *
	 * @param permissionIds
	 * @param userInfoBo
	 */
	private void verifyAddRolePermission(List<Long> permissionIds, UserInfoBo userInfoBo) {
		// 获取能添加的所有菜单 id
		List<MenuRes> menuRes = getCurrentSystemMenus();
		permissionIds.forEach(p -> {
			boolean exist = menuRes.stream().anyMatch(m -> m.getId().equals(p));
			// 不存在，没有权限操作
			if (!exist) {
				throw new BusinessException(ApiResultType.ROLE_INFO_INVALID_OPERATION.getCode());
			}
		});
	}
}
