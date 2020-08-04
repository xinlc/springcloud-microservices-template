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
 * 角色表
 * @author Leo
 * @since 2020-02-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ums_role_info")
@ApiModel(value="UmsRoleInfo对象", description="角色表")
public class UmsRoleInfo extends Model<UmsRoleInfo> {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

	@ApiModelProperty(value = "组织ID（purchaser_info.id，supplier_info.id，audit_info.id，ums_crm_info.id）")
	private Long orgId;

	@ApiModelProperty(value = "组织类型：1->采购方；2->供应方；3->审核机关；4->CRM")
	private Integer orgType;

    @ApiModelProperty(value = "角色标题")
    private String title;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "角色类型：99->系统级的超级管理员；98->平台级的超级管理员；0->用户自定义")
    private Integer roleType;

    @ApiModelProperty(value = "启用状态：0->禁用；1->启用；")
    private Integer roleStatus;

    @ApiModelProperty(value = "排序值，越小越靠前")
    private Integer sortOrder;

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


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
