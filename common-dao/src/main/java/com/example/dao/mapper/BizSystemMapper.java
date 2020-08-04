package com.example.dao.mapper;

import com.example.dao.entity.BizSystem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * [系]业务系统表 Mapper 接口
 * @author Leo
 * @since 2020-02-24
 */
@Mapper
@Repository
public interface BizSystemMapper extends BaseMapper<BizSystem> {
    /**
     * 逻辑删除操作
     * @param id
     */
    void deleteBizSystemById(Long id);
}
