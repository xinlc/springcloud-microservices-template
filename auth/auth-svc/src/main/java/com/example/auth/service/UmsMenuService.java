package com.example.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.dao.entity.UmsMenu;

/**
 * [系]菜单表 服务类
 *
 * @author Leo
 * @since 2020.08.01
 */
public interface UmsMenuService extends IService<UmsMenu> {

	/**
	 * [系]菜单表添加
	 *
	 * @param umsMenu
	 * @return
	 */
	boolean saveUmsMenu(UmsMenu umsMenu);

	/**
	 * [系]菜单表修改
	 *
	 * @param umsMenu
	 * @return
	 */
	boolean updateUmsMenuById(UmsMenu umsMenu);

	/**
	 * [系]菜单表删除
	 *
	 * @param id
	 * @return
	 */
	void deleteUmsMenuById(Long id);

	/**
	 * [系]菜单表详情
	 *
	 * @param id
	 * @return
	 */
	UmsMenu selectUmsMenuById(Long id);
}
