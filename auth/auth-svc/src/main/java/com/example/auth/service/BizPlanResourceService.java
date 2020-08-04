package com.example.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.dao.entity.BizPlanResource;

/**
 * [系]方案和资源模块关系表 服务类
 *
 * @author Leo
 * @since 2020.08.01
 */
public interface BizPlanResourceService extends IService<BizPlanResource> {

	/**
	 * [系]方案和资源模块关系表添加
	 *
	 * @param bizPlanResource
	 * @return
	 */
	boolean saveBizPlanResource(BizPlanResource bizPlanResource);

	/**
	 * [系]方案和资源模块关系表修改
	 *
	 * @param bizPlanResource
	 * @return
	 */
	boolean updateBizPlanResourceById(BizPlanResource bizPlanResource);

	/**
	 * [系]方案和资源模块关系表删除
	 *
	 * @param id
	 * @return
	 */
	void deleteBizPlanResourceById(Long id);

	/**
	 * [系]方案和资源模块关系表详情
	 *
	 * @param id
	 * @return
	 */
	BizPlanResource selectBizPlanResourceById(Long id);
}
