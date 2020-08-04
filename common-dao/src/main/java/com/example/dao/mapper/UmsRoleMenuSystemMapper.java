package com.example.dao.mapper;

import com.example.dao.entity.UmsRoleMenuSystem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 角色和菜单关系 Mapper 接口
 * @author Shawn
 * @since 2020-03-17
 */
@Mapper
@Repository
public interface UmsRoleMenuSystemMapper extends BaseMapper<UmsRoleMenuSystem> {
    /**
     * 逻辑删除操作
     * @param id
     */
    void deleteUmsRoleMenuSystemById(Long id);
}
