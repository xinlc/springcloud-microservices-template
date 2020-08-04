package com.example.auth.dto.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 菜单信息
 *
 * @author Leo
 * @since 2020.08.01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(description = "菜单信息")
public class MenuRes implements Serializable {

	private static final long serialVersionUID = -3349190551920357959L;

	@ApiModelProperty(value = "主键")
	private Long id;

	@ApiModelProperty(value = "父级ID")
	private Long pid;

	@ApiModelProperty(value = "标题")
	private String title;

	@ApiModelProperty(value = "权限标识（如：ums:user:add）")
	private String perms;

	@ApiModelProperty(value = "类型：0->目录；1->菜单；2->按钮；")
	private Integer menuType;

	@ApiModelProperty(value = "前端路由名称")
	private String menuName;

	@ApiModelProperty(value = "前端路由地址")
	private String uri;

	@ApiModelProperty(value = "前端图标")
	private String icon;

	@ApiModelProperty(value = "控制前端隐藏：0->显示；1->隐藏；")
	private Integer hidden;
}
