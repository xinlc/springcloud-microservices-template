package com.example.dao.mapper;

import com.example.dao.entity.BizPlanResource;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * [系]方案和资源模块关系表 Mapper 接口
 * @author Leo
 * @since 2020-02-24
 */
@Mapper
@Repository
public interface BizPlanResourceMapper extends BaseMapper<BizPlanResource> {
    /**
     * 逻辑删除操作
     * @param id
     */
    void deleteBizPlanResourceById(Long id);
}
