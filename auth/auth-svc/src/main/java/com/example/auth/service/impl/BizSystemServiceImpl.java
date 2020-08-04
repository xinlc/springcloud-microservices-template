package com.example.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.auth.service.BizSystemService;
import com.example.dao.entity.BizSystem;
import com.example.dao.mapper.BizSystemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * [系]业务系统表 服务实现类
 *
 * @author Leo
 * @since 2020.08.01
 */
@Service
public class BizSystemServiceImpl extends ServiceImpl<BizSystemMapper, BizSystem> implements BizSystemService {

	@Autowired
	public BizSystemMapper bizSystemMapper;

	@Override
	public boolean saveBizSystem(BizSystem bizSystem) {
		return this.saveOrUpdate(bizSystem);
	}

	@Override
	public boolean updateBizSystemById(BizSystem bizSystem) {
		return this.updateById(bizSystem);
	}

	@Override
	public void deleteBizSystemById(Long id) {
		bizSystemMapper.deleteBizSystemById(id);
	}

	@Override
	public BizSystem selectBizSystemById(Long id) {
		return this.getById(id);
	}
}
