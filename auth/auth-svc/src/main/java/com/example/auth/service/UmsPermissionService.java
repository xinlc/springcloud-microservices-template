package com.example.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.auth.dto.res.MenuRes;
import com.example.auth.dto.res.PermissionRes;
import com.example.auth.dto.res.TenantConfigRes;
import com.example.common.bo.UserInfoBo;
import com.example.dao.entity.UmsPermission;

import java.util.List;

/**
 * [系]权限表 服务类
 *
 * @author Leo
 * @since 2020.08.01
 */
public interface UmsPermissionService extends IService<UmsPermission> {

	/**
	 * 获取租户配置
	 *
	 * @param domain domain
	 * @return 租户配置
	 */
	TenantConfigRes getTenantConfigByDomain(String domain);

	/**
	 * 鉴权
	 *
	 * @param authentication authentication
	 * @param systemId       systemId
	 * @return 用户信息
	 */
	UserInfoBo authUserInfo(String authentication, String systemId);

	/**
	 * 获取指定用户ID所拥有的权限菜单
	 *
	 * @param userInfoBo 当前用户
	 * @return 权限列表
	 */
	List<MenuRes> getCurrentUserMenusList(UserInfoBo userInfoBo);

	/**
	 * 获取指定用户ID所拥有的权限列表
	 *
	 * @param userInfoBo 当前用户
	 * @return 权限列表
	 */
	List<PermissionRes> getCurrentUserUmsPermission(UserInfoBo userInfoBo);

	/**
	 * 鉴权
	 *
	 * @param userInfoBo 当前用户
	 * @return 是否有权限
	 */
	boolean authz(String[] permissions, UserInfoBo userInfoBo);

	/**
	 * 获取指定用户ID所拥有的权限菜单-扩展菜单
	 *
	 * @param userInfoBo 当前用户
	 * @return 菜单列表
	 */
	List<MenuRes> getCurrentUserMenusExtList(UserInfoBo userInfoBo);

}
