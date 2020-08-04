package com.example.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.auth.service.BizPlanResourceService;
import com.example.dao.entity.BizPlanResource;
import com.example.dao.mapper.BizPlanResourceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * [系]方案和资源模块关系表 服务实现类
 *
 * @author Leo
 * @since 2020.08.01
 */
@Service
public class BizPlanResourceServiceImpl extends ServiceImpl<BizPlanResourceMapper, BizPlanResource> implements BizPlanResourceService {

	@Autowired
	public BizPlanResourceMapper bizPlanResourceMapper;

	@Override
	public boolean saveBizPlanResource(BizPlanResource bizPlanResource) {
		return this.saveOrUpdate(bizPlanResource);
	}

	@Override
	public boolean updateBizPlanResourceById(BizPlanResource bizPlanResource) {
		return this.updateById(bizPlanResource);
	}

	@Override
	public void deleteBizPlanResourceById(Long id) {
		bizPlanResourceMapper.deleteBizPlanResourceById(id);
	}

	@Override
	public BizPlanResource selectBizPlanResourceById(Long id) {
		return this.getById(id);
	}
}
