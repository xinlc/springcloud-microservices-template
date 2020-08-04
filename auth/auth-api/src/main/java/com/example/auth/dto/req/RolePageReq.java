package com.example.auth.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 角色管理分页查询
 *
 * @author Leo
 * @since 2020.08.01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(description = "角色管理分页查询")
public class RolePageReq implements Serializable {

	private static final long serialVersionUID = 3751910522119536463L;

	@ApiModelProperty(value = "启用状态：0->已禁用；1->已启用；")
	private Integer roleStatus;
}
