package com.example.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.dao.entity.BizResourceCategory;

/**
 * [系]业务系统资源（功能模块） 服务类
 *
 * @author Leo
 * @since 2020.08.01
 */
public interface BizResourceCategoryService extends IService<BizResourceCategory> {

	/**
	 * [系]业务系统资源（功能模块）添加
	 *
	 * @param bizResourceCategory
	 * @return
	 */
	boolean saveBizResourceCategory(BizResourceCategory bizResourceCategory);

	/**
	 * [系]业务系统资源（功能模块）修改
	 *
	 * @param bizResourceCategory
	 * @return
	 */
	boolean updateBizResourceCategoryById(BizResourceCategory bizResourceCategory);

	/**
	 * [系]业务系统资源（功能模块）删除
	 *
	 * @param id
	 * @return
	 */
	void deleteBizResourceCategoryById(Long id);

	/**
	 * [系]业务系统资源（功能模块）详情
	 *
	 * @param id
	 * @return
	 */
	BizResourceCategory selectBizResourceCategoryById(Long id);
}
