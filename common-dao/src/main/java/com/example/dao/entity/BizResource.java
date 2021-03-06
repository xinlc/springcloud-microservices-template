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
 * [系]业务系统资源（功能模块）
 * @author Leo
 * @since 2020-02-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("biz_resource")
@ApiModel(value="BizResource对象", description="[系]业务系统资源（功能模块）")
public class BizResource extends Model<BizResource> {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "资源名称")
    private String resName;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "排序值，越小越靠前")
    private Integer sortOrder;

    @ApiModelProperty(value = "资源分类ID（biz_resource_category.id）")
    private Long categoryId;

    @ApiModelProperty(value = "业务系统ID（biz_system.id）")
    private Long systemId;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
