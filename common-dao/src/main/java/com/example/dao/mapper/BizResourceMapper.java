package com.example.dao.mapper;

import com.example.dao.entity.BizResource;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * [系]业务系统资源（功能模块） Mapper 接口
 * @author Leo
 * @since 2020-02-24
 */
@Mapper
@Repository
public interface BizResourceMapper extends BaseMapper<BizResource> {
    /**
     * 逻辑删除操作
     * @param id
     */
    void deleteBizResourceById(Long id);
}
