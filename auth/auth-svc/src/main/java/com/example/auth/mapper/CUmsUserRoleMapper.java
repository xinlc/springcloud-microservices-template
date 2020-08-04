package com.example.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.auth.dto.res.*;
import com.example.dao.entity.UmsUserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户和角色关系表 Mapper 接口
 *
 * @author Leo
 * @since 2020.08.01
 */
@Mapper
@Repository
public interface CUmsUserRoleMapper extends BaseMapper<UmsUserRole> {
	/**
	 * 逻辑删除操作
	 *
	 * @param id
	 */
	void deleteUmsUserRoleById(Long id);

	/**
	 * 根据用户id获取其业务角色
	 *
	 * @param userId
	 * @return
	 */
	List<RoleInfoRes> getRoleInfoResListByUserId(@Param("userId") Long userId);

}
