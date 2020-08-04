package com.example.auth.dto.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


/**
 * 角色信息
 *
 * @author Leo
 * @since 2020.08.01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(description="角色信息")
public class RoleInfoRes implements Serializable {

	private static final long serialVersionUID = 6918450381338118352L;

	@ApiModelProperty(value = "主键")
	private Long id;

	@ApiModelProperty(value = "角色标题")
	private String title;

	@ApiModelProperty(value = "描述")
	private String description;

	@ApiModelProperty(value = "启用状态：0->禁用；1->启用；")
	private Integer roleStatus = 1;

	@ApiModelProperty(value = "排序值，越小越靠前")
	private Integer sortOrder;

	@ApiModelProperty(value = "角色来源标识：1->业务角色；2->系统预设；")
	private Integer roleFlag;
}
