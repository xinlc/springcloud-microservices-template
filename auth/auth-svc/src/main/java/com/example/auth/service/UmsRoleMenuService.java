package com.example.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.dao.entity.UmsRoleMenu;

/**
 * 角色和菜单关系 服务类
 *
 * @author Leo
 * @since 2020.08.01
 */
public interface UmsRoleMenuService extends IService<UmsRoleMenu> {

	/**
	 * 角色和菜单关系添加
	 *
	 * @param umsRoleMenu
	 * @return
	 */
	boolean saveUmsRoleMenu(UmsRoleMenu umsRoleMenu);

	/**
	 * 角色和菜单关系修改
	 *
	 * @param umsRoleMenu
	 * @return
	 */
	boolean updateUmsRoleMenuById(UmsRoleMenu umsRoleMenu);

	/**
	 * 角色和菜单关系删除
	 *
	 * @param id
	 * @return
	 */
	void deleteUmsRoleMenuById(Long id);

	/**
	 * 角色和菜单关系详情
	 *
	 * @param id
	 * @return
	 */
	UmsRoleMenu selectUmsRoleMenuById(Long id);
}
