package com.example.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.dao.entity.BizPlan;

/**
 * [系]方案 服务类
 *
 * @author Leo
 * @since 2020.08.01
 */
public interface BizPlanService extends IService<BizPlan> {

	/**
	 * [系]方案添加
	 *
	 * @param bizPlan
	 * @return
	 */
	boolean saveBizPlan(BizPlan bizPlan);

	/**
	 * [系]方案修改
	 *
	 * @param bizPlan
	 * @return
	 */
	boolean updateBizPlanById(BizPlan bizPlan);

	/**
	 * [系]方案删除
	 *
	 * @param id
	 * @return
	 */
	void deleteBizPlanById(Long id);

	/**
	 * [系]方案详情
	 *
	 * @param id
	 * @return
	 */
	BizPlan selectBizPlanById(Long id);
}
