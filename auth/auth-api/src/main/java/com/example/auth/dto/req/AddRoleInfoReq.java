package com.example.auth.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * 添加角色信息
 *
 * @author Leo
 * @since 2020.08.01
 */
@Data
@ApiModel(description = "角色信息")
public class AddRoleInfoReq implements Serializable {

	private static final long serialVersionUID = -3136429971597373198L;

	@NotBlank
	@Size(max = 30, message = "角色标题为1-30个字符")
	@ApiModelProperty(value = "角色标题")
	private String title;

	@Size(max = 100, message = "描述最多100个字符")
	@ApiModelProperty(value = "描述")
	private String description;

	@Range(min = 0, max = 1, message = "请输入正确参数")
	@ApiModelProperty(value = "启用状态：0->禁用；1->启用；")
	private Integer roleStatus;

	@NotNull
	@Size(min = 1, message = "请选择权限")
	@ApiModelProperty(value = "权限ID列表")
	List<Long> permissionIds;

}
