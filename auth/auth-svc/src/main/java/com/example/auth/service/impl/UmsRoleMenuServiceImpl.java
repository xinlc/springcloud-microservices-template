package com.example.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.auth.service.UmsRoleMenuService;
import com.example.dao.entity.UmsRoleMenu;
import com.example.dao.mapper.UmsRoleMenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 角色和菜单关系 服务实现类
 *
 * @author Leo
 * @since 2020.08.01
 */
@Service
public class UmsRoleMenuServiceImpl extends ServiceImpl<UmsRoleMenuMapper, UmsRoleMenu> implements UmsRoleMenuService {

	@Autowired
	public UmsRoleMenuMapper umsRoleMenuMapper;

	@Override
	public boolean saveUmsRoleMenu(UmsRoleMenu umsRoleMenu) {
		return this.saveOrUpdate(umsRoleMenu);
	}

	@Override
	public boolean updateUmsRoleMenuById(UmsRoleMenu umsRoleMenu) {
		return this.updateById(umsRoleMenu);
	}

	@Override
	public void deleteUmsRoleMenuById(Long id) {
		umsRoleMenuMapper.deleteUmsRoleMenuById(id);
	}

	@Override
	public UmsRoleMenu selectUmsRoleMenuById(Long id) {
		return this.getById(id);
	}
}
