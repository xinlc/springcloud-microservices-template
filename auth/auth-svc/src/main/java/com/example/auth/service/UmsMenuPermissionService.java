package com.example.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.dao.entity.UmsMenuPermission;

/**
 * [系]菜单和权限关系表 服务类
 *
 * @author Leo
 * @since 2020.08.01
 */
public interface UmsMenuPermissionService extends IService<UmsMenuPermission> {

	/**
	 * [系]菜单和权限关系表添加
	 *
	 * @param umsMenuPermission
	 * @return
	 */
	boolean saveUmsMenuPermission(UmsMenuPermission umsMenuPermission);

	/**
	 * [系]菜单和权限关系表修改
	 *
	 * @param umsMenuPermission
	 * @return
	 */
	boolean updateUmsMenuPermissionById(UmsMenuPermission umsMenuPermission);

	/**
	 * [系]菜单和权限关系表删除
	 *
	 * @param id
	 * @return
	 */
	void deleteUmsMenuPermissionById(Long id);

	/**
	 * [系]菜单和权限关系表详情
	 *
	 * @param id
	 * @return
	 */
	UmsMenuPermission selectUmsMenuPermissionById(Long id);
}
