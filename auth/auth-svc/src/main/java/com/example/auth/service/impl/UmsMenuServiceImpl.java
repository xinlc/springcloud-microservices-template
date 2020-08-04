package com.example.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.auth.service.UmsMenuService;
import com.example.dao.entity.UmsMenu;
import com.example.dao.mapper.UmsMenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * [系]菜单表 服务实现类
 *
 * @author Leo
 * @since 2020.08.01
 */
@Service
public class UmsMenuServiceImpl extends ServiceImpl<UmsMenuMapper, UmsMenu> implements UmsMenuService {

	@Autowired
	public UmsMenuMapper umsMenuMapper;

	@Override
	public boolean saveUmsMenu(UmsMenu umsMenu) {
		return this.saveOrUpdate(umsMenu);
	}

	@Override
	public boolean updateUmsMenuById(UmsMenu umsMenu) {
		return this.updateById(umsMenu);
	}

	@Override
	public void deleteUmsMenuById(Long id) {
		umsMenuMapper.deleteUmsMenuById(id);
	}

	@Override
	public UmsMenu selectUmsMenuById(Long id) {
		return this.getById(id);
	}
}
