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
 * [系]权限表
 * @author Leo
 * @since 2020-02-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ums_permission")
@ApiModel(value="UmsPermission对象", description="[系]权限表")
public class UmsPermission extends Model<UmsPermission> {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "父级ID(permission.id)")
    private Long pid;

    @ApiModelProperty(value = "权限标题")
    private String title;

    @ApiModelProperty(value = "接口前缀")
    private String apiPrefix;

    @ApiModelProperty(value = "接口请求方法（如：GET、POST、PUT、DELETE）")
    private String apiMethod;

    @ApiModelProperty(value = "接口路径（如：/ums/addUser）")
    private String apiPath;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "逻辑删除状态：0->有效；NULL->删除")
    private Integer deleteFlag;

	@ApiModelProperty(value = "权限白名单标识：1->是白名单；")
	private Integer whiteListFlag;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
