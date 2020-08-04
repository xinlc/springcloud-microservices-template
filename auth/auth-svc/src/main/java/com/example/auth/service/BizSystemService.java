package com.example.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.dao.entity.BizSystem;

/**
 * [系]业务系统表 服务类
 *
 * @author Leo
 * @since 2020.08.01
 */
public interface BizSystemService extends IService<BizSystem> {

	/**
	 * [系]业务系统表添加
	 *
	 * @param bizSystem
	 * @return
	 */
	boolean saveBizSystem(BizSystem bizSystem);

	/**
	 * [系]业务系统表修改
	 *
	 * @param bizSystem
	 * @return
	 */
	boolean updateBizSystemById(BizSystem bizSystem);

	/**
	 * [系]业务系统表删除
	 *
	 * @param id
	 * @return
	 */
	void deleteBizSystemById(Long id);

	/**
	 * [系]业务系统表详情
	 *
	 * @param id
	 * @return
	 */
	BizSystem selectBizSystemById(Long id);
}
