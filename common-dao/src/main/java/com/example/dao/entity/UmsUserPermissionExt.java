package com.example.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * [系]用户权限扩展表
 * @author Leo
 * @since 2020-05-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ums_user_permission_ext")
@ApiModel(value="UmsUserPermissionExt对象", description="[系]用户权限扩展表")
public class UmsUserPermissionExt extends Model<UmsUserPermissionExt> {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "菜单ID（ums_menu_ext.id)")
    private Long menuId;

    @ApiModelProperty(value = "用户ID（user_info.id）")
    private Long userId;

    @ApiModelProperty(value = "逻辑删除状态：0->有效；NULL->删除")
    private Integer deleteFlag;

    @ApiModelProperty(value = "租户ID")
    private Integer tenantId;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
