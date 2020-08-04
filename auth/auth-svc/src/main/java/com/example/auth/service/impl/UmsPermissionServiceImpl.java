package com.example.auth.service.impl;

import com.alibaba.fastjson.JSON;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.auth.config.TerminalProps;
import com.example.auth.config.TerminalConfigProps;
import com.example.auth.dto.res.MenuRes;
import com.example.auth.dto.res.PermissionRes;
import com.example.auth.dto.res.TenantConfigRes;
import com.example.auth.emum.RoleTypeEnum;
import com.example.auth.mapper.CUmsUserRoleMapper;
import com.example.auth.service.UmsPermissionService;
import com.example.auth.service.UmsRoleInfoService;
import com.example.auth.utils.TerminalUtil;
import com.example.common.api.ApiResultType;
import com.example.common.constant.ApplicationConst;
import com.example.common.constant.BusinessConst;
import com.example.common.exception.BusinessException;
import com.example.common.bo.UserInfoBo;
import com.example.common.redis.RedisService;
import com.example.common.utils.JwtTokenUtil;
import com.example.dao.entity.*;
import com.example.dao.mapper.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * [系]权限表 服务实现类
 *
 * @author Leo
 * @since 2020.08.01
 */
@Service
@Slf4j
public class UmsPermissionServiceImpl extends ServiceImpl<UmsPermissionMapper, UmsPermission> implements UmsPermissionService {

	@Autowired
	private TerminalConfigProps terminalConfigProps;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private RedisService redisService;

	@Autowired
	public UmsPermissionMapper umsPermissionMapper;

	@Autowired
	public TenantConfigMapper tenantConfigMapper;

	@Autowired
	public UmsMenuMapper umsMenuMapper;

	@Autowired
	public UmsRoleMenuMapper umsRoleMenuMapper;

	@Autowired
	public CUmsUserRoleMapper cUmsUserRoleMapper;

	@Autowired
	public UmsRoleInfoMapper umsRoleInfoMapper;

	@Autowired
	public UmsRoleInfoService umsRoleInfoService;

	@Autowired
	public UmsMenuPermissionMapper umsMenuPermissionMapper;

	@Autowired
	public UmsRoleMenuSystemMapper umsRoleMenuSystemMapper;

	@Autowired
	public UmsUserPermissionExtMapper umsUserPermissionExtMapper;

	@Autowired
	public UmsMenuExtMapper umsMenuExtMapper;

	@Cached(name = "UmsPermissionServiceImpl.getTenantConfigByDomain.", key = "#domain",
			expire = 60, cacheType = CacheType.BOTH)
	@Override
	public TenantConfigRes getTenantConfigByDomain(String domain) {
		TenantConfig tenantConfig = tenantConfigMapper.selectOne(Wrappers.<TenantConfig>lambdaQuery()
				.eq(TenantConfig::getDomain, domain)
				.eq(TenantConfig::getDeleteFlag, 0));

		if (null == tenantConfig) {
			throw new BusinessException(ApiResultType.REQUEST_ILLEGAL.getCode());
		}
		return modelMapper.map(tenantConfig, TenantConfigRes.class);
	}

	@Override
	public UserInfoBo authUserInfo(String authentication, String systemId) {
		TerminalProps terminalProps = TerminalUtil.getTerminalConfig(systemId, terminalConfigProps);
		JwtTokenUtil jwtTokenUtil = JwtTokenUtil.builder()
				.secret(terminalProps.getSecret())
				.payloadSecret(terminalProps.getPayloadSecret())
				.tokenHead(terminalProps.getTokenHead())
				.refreshInterval(terminalProps.getRefreshInterval())
				.expiration(terminalProps.getExpiration())
				.build();

		// 从头部获取 JWT
		String token = jwtTokenUtil.getTokenByAuthorization(authentication);

		// 判定登录令牌是否存在
		if (StringUtils.isBlank(token)) {
			throw new BusinessException(ApiResultType.LOGON_FILE.getCode());
		}

		String redisKey = jwtTokenUtil.getSignatureFromToken(token);
		Object user = redisService.get(ApplicationConst.TOKEN_PREFIX + redisKey);
		if (null == user) {
			throw new BusinessException(ApiResultType.LOGON_FILE.getCode());
		}
		UserInfoBo userInfoBo = JSON.parseObject(user.toString(), UserInfoBo.class);

		if (jwtTokenUtil.validateToken(token, userInfoBo)) {
			String newToken = jwtTokenUtil.refreshToken(token);
			// 刷新成功，更新 redis 和 cookie
			if (!token.equals(newToken)) {
				// 使用签名作为 redis 缓存 key
				String newRedisKey = jwtTokenUtil.getSignatureFromToken(newToken);
				long expiration = terminalProps.getExpiration();

				// 更新 redis
				userInfoBo.setToken(newToken);
				userInfoBo.setExpireTime(String.valueOf(expiration * 1000));

				// 删除老的token
				redisService.remove(ApplicationConst.TOKEN_PREFIX + redisKey);

				// 更新新的token
				redisService.set(ApplicationConst.TOKEN_PREFIX + newRedisKey, JSON.toJSONString(userInfoBo), expiration);
				redisService.set(ApplicationConst.TOKEN_KEY_PREFIX + userInfoBo.getUserId(), newRedisKey, expiration);
			}
		} else {
			// Token 失效
			throw new BusinessException(ApiResultType.LOGON_FILE.getCode());
		}
		return userInfoBo;
	}

	@Cached(name = "UmsPermissionServiceImpl.getCurrentUserMenusList.", key = "#userInfoBo.userId",
			expire = 60, cacheType = CacheType.BOTH)
	@Override
	public List<MenuRes> getCurrentUserMenusList(UserInfoBo userInfoBo) {

		// 获取用户所有角色关联
		List<UmsUserRole> umsUserRoles = cUmsUserRoleMapper.selectList(Wrappers.<UmsUserRole>lambdaQuery()
				.eq(UmsUserRole::getUserId, userInfoBo.getUserId())
				.eq(UmsUserRole::getDeleteFlag, 0));

		// 获取用户业务角色id
		List<Long> bizRoleIds = umsUserRoles
				.stream()
				.filter(i -> BusinessConst.BUSINESS_ROLE_FLAG.equals(i.getRoleFlag()))
				.map(UmsUserRole::getRoleId)
				.collect(Collectors.toList());

		// 获取系统预设角色id
		List<Long> sysRoleIds = umsUserRoles
				.stream()
				.filter(i -> BusinessConst.SYSTEM_ROLE_FLAG.equals(i.getRoleFlag()))
				.map(UmsUserRole::getRoleId)
				.collect(Collectors.toList());

		if (bizRoleIds.size() <= 0 && sysRoleIds.size() <= 0) {
			throw new BusinessException(ApiResultType.ROLE_NOT_EXISTS.getCode());
		}

		boolean isAdmin = false;
		if (bizRoleIds.size() > 0) {
			// 获取所有角色信息
			List<UmsRoleInfo> umsRoleInfos = umsRoleInfoMapper.selectList(Wrappers.<UmsRoleInfo>lambdaQuery()
					.in(UmsRoleInfo::getId, bizRoleIds)
					.eq(UmsRoleInfo::getRoleStatus, 1)
					.eq(UmsRoleInfo::getDeleteFlag, 0));

			// 查找用户是否是超级管理员，或平台管理员
			isAdmin = umsRoleInfos.stream()
					.anyMatch(r -> RoleTypeEnum.getValue(r.getRoleType()).equals(RoleTypeEnum.CRM_ADMIN)
							|| RoleTypeEnum.getValue(r.getRoleType()).equals(RoleTypeEnum.PLATFORM_ADMIN));
		}

		List<MenuRes> menuRes = new ArrayList<>();

		// 是管理员，查询所有菜单
		if (isAdmin) {
			menuRes = umsRoleInfoService.getCurrentSystemMenus();
		} else {

			// 获取菜单id
			List<Long> menuIds = new ArrayList<>();
			if (bizRoleIds.size() > 0) {
				List <Long> bizMenuIds = umsRoleMenuMapper.selectList(Wrappers.<UmsRoleMenu>lambdaQuery()
						.in(UmsRoleMenu::getRoleId, bizRoleIds)
						.eq(UmsRoleMenu::getDeleteFlag, 0))
						.stream()
						.map(UmsRoleMenu::getMenuId)
						.distinct()
						.collect(Collectors.toList());
				menuIds.addAll(bizMenuIds);
			}

			// 回去系统预设菜单id
			if (sysRoleIds.size() > 0) {
				List <Long> sysMenuIds = umsRoleMenuSystemMapper.selectList(Wrappers.<UmsRoleMenuSystem>lambdaQuery()
						.in(UmsRoleMenuSystem::getRoleId, sysRoleIds)
						.eq(UmsRoleMenuSystem::getDeleteFlag, 0))
						.stream()
						.map(UmsRoleMenuSystem::getMenuId)
						.collect(Collectors.toList());
				menuIds.addAll(sysMenuIds);
			}

			// 去重
			menuIds = menuIds.stream().distinct().collect(Collectors.toList());

			// 查询角色所能操作的菜单
			List<UmsMenu> umsMenu = umsMenuMapper.selectList(Wrappers.<UmsMenu>lambdaQuery()
					.in(UmsMenu::getId, menuIds)
					.orderByAsc(UmsMenu::getSortOrder));

			menuRes = modelMapper.map(umsMenu, new TypeToken<List<MenuRes>>() {
			}.getType());
		}

		return menuRes;
	}

	@Cached(name = "UmsPermissionServiceImpl.getCurrentUserUmsPermission.", key = "#userInfoBo.userId",
			expire = 60, cacheType = CacheType.BOTH)
	@Override
	public List<PermissionRes> getCurrentUserUmsPermission(UserInfoBo userInfoBo) {
		// 获取当前用户能操作的所有菜单id
		List<Long> menuIds = getCurrentUserMenusList(userInfoBo)
				.stream()
				.map(MenuRes::getId)
				.collect(Collectors.toList());

		// 获取权限id
		List<Long> permissionIds = umsMenuPermissionMapper.selectList(Wrappers.<UmsMenuPermission>lambdaQuery()
				.in(UmsMenuPermission::getMenuId, menuIds))
				.stream()
				.map(UmsMenuPermission::getPermissionId)
				.distinct()
				.collect(Collectors.toList());

		// 获取权限
		List<UmsPermission> umsPermissions = umsPermissionMapper.selectList(Wrappers.<UmsPermission>lambdaQuery()
				.in(UmsPermission::getId, permissionIds)
				.eq(UmsPermission::getDeleteFlag, 0)
				.or(c -> c.eq(UmsPermission::getWhiteListFlag, 1)));

		List<PermissionRes> permissionRes = modelMapper.map(umsPermissions, new TypeToken<List<PermissionRes>>() {
		}.getType());

		return permissionRes;
	}

	// 可以加缓存
	@Override
	public boolean authz(String[] permissions, UserInfoBo userInfoBo) {
		List<UmsMenuExt> umsMenuExts = getMenuExtByUserId(userInfoBo);
		if (umsMenuExts.size() > 0) {
			return Arrays.stream(permissions)
					.anyMatch(s -> umsMenuExts.stream().anyMatch(m -> m.getPerms().equals(s)));
		}
		return false;
	}

	@Override
	public List<MenuRes> getCurrentUserMenusExtList(UserInfoBo userInfoBo) {
		List<UmsMenuExt> umsMenuExts = getMenuExtByUserId(userInfoBo);

		List<MenuRes> menuResList = modelMapper.map(umsMenuExts, new TypeToken<List<MenuRes>>() {
		}.getType());
		return menuResList;
	}

	/**
	 * 获取系统扩展菜单-指定用户
	 *
	 * @param userInfoBo
	 * @return
	 */
	private List<UmsMenuExt> getMenuExtByUserId(UserInfoBo userInfoBo) {
		List<UmsUserPermissionExt> umsUserPermissionExt = umsUserPermissionExtMapper.selectList(Wrappers.<UmsUserPermissionExt>lambdaQuery()
				.eq(UmsUserPermissionExt::getDeleteFlag, 0)
				.eq(UmsUserPermissionExt::getUserId, userInfoBo.getUserId())
		);

		List<Long> menuIds = umsUserPermissionExt.stream().map(UmsUserPermissionExt::getMenuId).collect(Collectors.toList());

		List<UmsMenuExt> umsMenuExts = new ArrayList<>();

		if (menuIds.size() > 0) {
			umsMenuExts = umsMenuExtMapper.selectList(Wrappers.<UmsMenuExt>lambdaQuery()
					.in(UmsMenuExt::getId, menuIds)
			);
		}
		return umsMenuExts;
	}

}
