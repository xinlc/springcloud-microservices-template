package com.example.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.auth.service.UmsMenuPermissionService;
import com.example.dao.entity.UmsMenuPermission;
import com.example.dao.mapper.UmsMenuPermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * [系]菜单和权限关系表 服务实现类
 *
 * @author Leo
 * @since 2020.08.01
 */
@Service
public class UmsMenuPermissionServiceImpl extends ServiceImpl<UmsMenuPermissionMapper, UmsMenuPermission> implements UmsMenuPermissionService {

	@Autowired
	public UmsMenuPermissionMapper umsMenuPermissionMapper;

	@Override
	public boolean saveUmsMenuPermission(UmsMenuPermission umsMenuPermission) {
		return this.saveOrUpdate(umsMenuPermission);
	}

	@Override
	public boolean updateUmsMenuPermissionById(UmsMenuPermission umsMenuPermission) {
		return this.updateById(umsMenuPermission);
	}

	@Override
	public void deleteUmsMenuPermissionById(Long id) {
		umsMenuPermissionMapper.deleteUmsMenuPermissionById(id);
	}

	@Override
	public UmsMenuPermission selectUmsMenuPermissionById(Long id) {
		return this.getById(id);
	}
}
