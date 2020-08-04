package com.example.auth.dto.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 权限
 *
 * @author Leo
 * @since 2020.08.01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(description = "权限对象")
public class PermissionRes implements Serializable {

	private static final long serialVersionUID = 2885999139575936478L;

	@ApiModelProperty(value = "主键")
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
}
