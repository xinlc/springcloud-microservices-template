package com.example.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.auth.service.BizResourceService;
import com.example.dao.entity.BizResource;
import com.example.dao.mapper.BizResourceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * [系]业务系统资源（功能模块） 服务实现类
 *
 * @author Leo
 * @since 2020.08.01
 */
@Service
public class BizResourceServiceImpl extends ServiceImpl<BizResourceMapper, BizResource> implements BizResourceService {

	@Autowired
	public BizResourceMapper bizResourceMapper;

	@Override
	public boolean saveBizResource(BizResource bizResource) {
		return this.saveOrUpdate(bizResource);
	}

	@Override
	public boolean updateBizResourceById(BizResource bizResource) {
		return this.updateById(bizResource);
	}

	@Override
	public void deleteBizResourceById(Long id) {
		bizResourceMapper.deleteBizResourceById(id);
	}

	@Override
	public BizResource selectBizResourceById(Long id) {
		return this.getById(id);
	}
}
