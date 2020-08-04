package com.example.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用户和角色关系表
 * @author Leo
 * @since 2020-02-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ums_user_role")
@ApiModel(value="UmsUserRole对象", description="用户和角色关系表")
public class UmsUserRole extends Model<UmsUserRole> {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "角色ID（role.id）")
    private Long roleId;

    @ApiModelProperty(value = "用户ID（user_info.id）")
    private Long userId;

    @ApiModelProperty(value = "逻辑删除状态：0->有效；NULL->删除")
    private Integer deleteFlag;

    @ApiModelProperty(value = "租户ID")
    private Integer tenantId;

    @ApiModelProperty(value = "创建时间（系统时间）")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "创建人ID")
    private Long createUserId;

    @ApiModelProperty(value = "更新时间（系统时间）")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "修改人ID")
    private Long updateUserId;

    @ApiModelProperty(value = "角色来源标识：1->业务角色；2->系统预设；")
    private Integer roleFlag;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
