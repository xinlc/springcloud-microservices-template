package com.example.dao.mapper;

import com.example.dao.entity.UmsMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * [系]菜单表 Mapper 接口
 * @author Leo
 * @since 2020-02-24
 */
@Mapper
@Repository
public interface UmsMenuMapper extends BaseMapper<UmsMenu> {
    /**
     * 逻辑删除操作
     * @param id
     */
    void deleteUmsMenuById(Long id);
}
