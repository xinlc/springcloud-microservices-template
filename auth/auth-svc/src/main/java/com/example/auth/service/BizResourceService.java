package com.example.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.dao.entity.BizResource;

/**
 * [系]业务系统资源（功能模块） 服务类
 *
 * @author Leo
 * @since 2020.08.01
 */
public interface BizResourceService extends IService<BizResource> {

	/**
	 * [系]业务系统资源（功能模块）添加
	 *
	 * @param bizResource
	 * @return
	 */
	boolean saveBizResource(BizResource bizResource);

	/**
	 * [系]业务系统资源（功能模块）修改
	 *
	 * @param bizResource
	 * @return
	 */
	boolean updateBizResourceById(BizResource bizResource);

	/**
	 * [系]业务系统资源（功能模块）删除
	 *
	 * @param id
	 * @return
	 */
	void deleteBizResourceById(Long id);

	/**
	 * [系]业务系统资源（功能模块）详情
	 *
	 * @param id
	 * @return
	 */
	BizResource selectBizResourceById(Long id);
}
