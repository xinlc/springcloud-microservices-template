package com.example.dao.mapper;

import com.example.dao.entity.BizPlan;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * [系]方案 Mapper 接口
 * @author Leo
 * @since 2020-02-24
 */
@Mapper
@Repository
public interface BizPlanMapper extends BaseMapper<BizPlan> {
    /**
     * 逻辑删除操作
     * @param id
     */
    void deleteBizPlanById(Long id);
}
