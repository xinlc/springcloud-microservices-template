package com.example.dao.mapper;

import com.example.dao.entity.UmsRoleMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 角色和菜单关系 Mapper 接口
 * @author Leo
 * @since 2020-02-24
 */
@Mapper
@Repository
public interface UmsRoleMenuMapper extends BaseMapper<UmsRoleMenu> {
    /**
     * 逻辑删除操作
     * @param id
     */
    void deleteUmsRoleMenuById(Long id);
}
