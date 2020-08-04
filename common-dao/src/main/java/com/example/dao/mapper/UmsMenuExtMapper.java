package com.example.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.dao.entity.UmsMenuExt;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * [系]菜单扩展表 Mapper 接口
 * @author Leo
 * @since 2020-05-28
 */
@Mapper
@Repository
public interface UmsMenuExtMapper extends BaseMapper<UmsMenuExt> {
    /**
     * 逻辑删除操作
     * @param id
     */
    void deleteUmsMenuExtById(Long id);
}
