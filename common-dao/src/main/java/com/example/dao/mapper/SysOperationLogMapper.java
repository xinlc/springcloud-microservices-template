package com.example.dao.mapper;

import com.example.dao.entity.SysOperationLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 操作日志记录 Mapper 接口
 * @author Leo
 * @since 2020-04-13
 */
@Mapper
@Repository
public interface SysOperationLogMapper extends BaseMapper<SysOperationLog> {
    /**
     * 逻辑删除操作
     * @param id
     */
    void deleteOperationLogById(Long id);
}
