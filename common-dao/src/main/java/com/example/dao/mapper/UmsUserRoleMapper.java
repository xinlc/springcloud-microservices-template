package com.example.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.dao.entity.UmsUserRole;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 用户和角色关系表 Mapper 接口
 * @author Leo
 * @since 2020-02-24
 */
@Mapper
@Repository
public interface UmsUserRoleMapper extends BaseMapper<UmsUserRole> {
    /**
     * 逻辑删除操作
     * @param id
     */
    void deleteUmsUserRoleById(Long id);
}
