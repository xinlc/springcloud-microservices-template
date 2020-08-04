package com.example.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * [系]菜单和权限关系表
 * @author Leo
 * @since 2020-02-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ums_menu_permission")
@ApiModel(value="UmsMenuPermission对象", description="[系]菜单和权限关系表")
public class UmsMenuPermission extends Model<UmsMenuPermission> {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "菜单ID（ums_menu.id）")
    private Long menuId;

    @ApiModelProperty(value = "权限ID（ums_permission.id）")
    private Long permissionId;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
