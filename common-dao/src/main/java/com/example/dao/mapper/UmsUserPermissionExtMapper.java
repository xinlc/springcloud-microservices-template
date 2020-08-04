package com.example.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.dao.entity.UmsUserPermissionExt;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * [系]用户权限扩展表 Mapper 接口
 * @author Leo
 * @since 2020-05-28
 */
@Mapper
@Repository
public interface UmsUserPermissionExtMapper extends BaseMapper<UmsUserPermissionExt> {
    /**
     * 逻辑删除操作
     * @param id
     */
    void deleteUmsUserPermissionExtById(Long id);
}
