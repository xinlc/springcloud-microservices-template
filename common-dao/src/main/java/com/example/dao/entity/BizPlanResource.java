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
 * [系]方案和资源模块关系表
 * @author Leo
 * @since 2020-02-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("biz_plan_resource")
@ApiModel(value="BizPlanResource对象", description="[系]方案和资源模块关系表")
public class BizPlanResource extends Model<BizPlanResource> {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "方案ID（biz_plan.id）")
    private Long planId;

    @ApiModelProperty(value = "资源ID（biz_resource.id）")
    private Long resourceId;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
