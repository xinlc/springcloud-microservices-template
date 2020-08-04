package com.example.dao.mapper;

import com.example.dao.entity.TenantConfig;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 租户配置信息表 Mapper 接口
 * @author Leo
 * @since 2019-10-23
 */
@Mapper
@Repository
public interface TenantConfigMapper extends BaseMapper<TenantConfig> {
    /**
     * 逻辑删除操作
     * @param id
     */
    void deleteTenantConfigById(Long id);
}
