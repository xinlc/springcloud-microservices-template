package com.example.dao.mapper;

import com.example.common.bo.UserInfoBo;
import com.example.dao.entity.UmsRoleInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.dao.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 角色表 Mapper 接口
 * @author Leo
 * @since 2020-02-24
 */
@Mapper
@Repository
public interface UmsRoleInfoMapper extends BaseMapper<UmsRoleInfo> {
    /**
     * 逻辑删除操作
     * @param id
     */
    void deleteUmsRoleInfoById(Long id);

    /**
     * 获取登录用户的所有角色
     * @param userInfoBo
     * @return
     */
    List<UmsRoleInfo> getUmsRoleInfoListByUser(UserInfoBo userInfoBo);

    /**
     * 获取指定用户的所有有效角色
     * @param userInfo
     * @return
     */
    List<UmsRoleInfo> getUmsRoleInfoListByUserInfo(UserInfo userInfo);
}
