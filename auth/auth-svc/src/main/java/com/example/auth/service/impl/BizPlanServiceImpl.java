package com.example.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.auth.service.BizPlanService;
import com.example.dao.entity.BizPlan;
import com.example.dao.mapper.BizPlanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * [系]方案 服务实现类
 *
 * @author Leo
 * @since 2020.08.01
 */
@Service
public class BizPlanServiceImpl extends ServiceImpl<BizPlanMapper, BizPlan> implements BizPlanService {

	@Autowired
	public BizPlanMapper bizPlanMapper;

	@Override
	public boolean saveBizPlan(BizPlan bizPlan) {
		return this.saveOrUpdate(bizPlan);
	}

	@Override
	public boolean updateBizPlanById(BizPlan bizPlan) {
		return this.updateById(bizPlan);
	}

	@Override
	public void deleteBizPlanById(Long id) {
		bizPlanMapper.deleteBizPlanById(id);
	}

	@Override
	public BizPlan selectBizPlanById(Long id) {
		return this.getById(id);
	}
}
