package com.example.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.auth.service.BizResourceCategoryService;
import com.example.dao.entity.BizResourceCategory;
import com.example.dao.mapper.BizResourceCategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * [系]业务系统资源（功能模块） 服务实现类
 *
 * @author Leo
 * @since 2020.08.01
 */
@Service
public class BizResourceCategoryServiceImpl extends ServiceImpl<BizResourceCategoryMapper, BizResourceCategory> implements BizResourceCategoryService {

	@Autowired
	public BizResourceCategoryMapper bizResourceCategoryMapper;

	@Override
	public boolean saveBizResourceCategory(BizResourceCategory bizResourceCategory) {
		return this.saveOrUpdate(bizResourceCategory);
	}

	@Override
	public boolean updateBizResourceCategoryById(BizResourceCategory bizResourceCategory) {
		return this.updateById(bizResourceCategory);
	}

	@Override
	public void deleteBizResourceCategoryById(Long id) {
		bizResourceCategoryMapper.deleteBizResourceCategoryById(id);
	}

	@Override
	public BizResourceCategory selectBizResourceCategoryById(Long id) {
		return this.getById(id);
	}
}
