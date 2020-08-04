package com.example.dao.mapper;

import com.example.dao.entity.UmsCrmInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * crm系统用户信息 Mapper 接口
 * @author Leo
 * @since 2020-02-24
 */
@Mapper
@Repository
public interface UmsCrmInfoMapper extends BaseMapper<UmsCrmInfo> {
    /**
     * 逻辑删除操作
     * @param id
     */
    void deleteUmsCrmInfoById(Long id);
}
