package com.example.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.example.auth.dto.req.AddRoleInfoReq;
import com.example.auth.dto.req.RolePageReq;
import com.example.auth.dto.req.UpdateRoleInfoReq;
import com.example.auth.dto.res.MenuRes;
import com.example.auth.dto.res.RoleInfoDetailRes;
import com.example.auth.dto.res.RoleInfoRes;
import com.example.common.bo.UserInfoBo;
import com.example.dao.entity.UmsRoleInfo;

import java.util.List;

/**
 * 角色服务类
 *
 * @author Leo
 * @since 2020.08.01
 */
public interface UmsRoleInfoService extends IService<UmsRoleInfo> {

	/**
	 * 获取角色列表
	 *
	 * @param userInfoBo 当前用户
	 * @param rolePageReq 搜索条件
	 * @return 角色列表
	 * @author Leo
	 * @date 2020.02.26
	 */
	PageInfo<RoleInfoRes> getRoleInfoList(UserInfoBo userInfoBo, Integer page, Integer limit, RolePageReq rolePageReq);

	/**
	 * 添加角色
	 *
	 * @param addRoleInfoReq 角色信息及权限信息
	 * @param userInfoBo     当前用户
	 * @return id
	 * @author Leo
	 * @date 2020.02.26
	 */
	Long addRoleInfo(AddRoleInfoReq addRoleInfoReq, UserInfoBo userInfoBo);

	/**
	 * 修改角色
	 *
	 * @param updateRoleInfoReq 角色信息及权限信息
	 * @param userInfoBo        当前用户
	 * @author Leo
	 * @date 2020.02.26
	 */
	void updateRoleInfo(UpdateRoleInfoReq updateRoleInfoReq, UserInfoBo userInfoBo);

	/**
	 * 获取角色详情
	 *
	 * @param id         角色id
	 * @param userInfoBo 当前用户
	 * @param roleFlag 角色来源标识：1->业务角色；2->系统预设；
	 * @return 角色详情
	 * @author Leo
	 * @date 2020.02.26
	 */
	RoleInfoDetailRes getRoleInfoDetailById(Long id, Integer roleFlag, UserInfoBo userInfoBo);

	/**
	 * 删除角色
	 *
	 * @param id         角色id
	 * @param userInfoBo 当前用户
	 * @author Leo
	 * @date 2020.02.26
	 */
	void deleteRoleInfoById(Long id, UserInfoBo userInfoBo);

	/**
	 * 获取指定系统菜单
	 *
	 * @return 菜单列表
	 * @author Leo
	 * @date 2020.02.26
	 */
	List<MenuRes> getCurrentSystemMenus();

}
