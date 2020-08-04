package com.example.dao.mapper;

import com.example.dao.entity.UmsRoleInfoSystem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 角色表 Mapper 接口
 * @author Shawn
 * @since 2020-03-17
 */
@Mapper
@Repository
public interface UmsRoleInfoSystemMapper extends BaseMapper<UmsRoleInfoSystem> {
    /**
     * 逻辑删除操作
     * @param id
     */
    void deleteUmsRoleInfoSystemById(Long id);
}
