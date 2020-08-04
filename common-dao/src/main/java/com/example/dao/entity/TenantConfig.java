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
 * [系]租户配置信息表
 * @author Leo
 * @since 2020-01-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tenant_config")
@ApiModel(value="TenantConfig对象", description="[系]租户配置信息表")
public class TenantConfig extends Model<TenantConfig> {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "平台域名")
    private String domain;

    @ApiModelProperty(value = "租户ID")
    private Integer tenantId;

	@ApiModelProperty(value = "方案ID")
	private Integer planId;

	@ApiModelProperty(value = "过期时间")
	private LocalDateTime expirationTime;

    @ApiModelProperty(value = "逻辑删除状态：0->有效；NULL->删除")
    private Integer deleteFlag;

    @ApiModelProperty(value = "租户描述")
    private String descriptions;

    @ApiModelProperty(value = "创建时间（系统时间）")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间（系统时间）")
    private LocalDateTime updateTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
