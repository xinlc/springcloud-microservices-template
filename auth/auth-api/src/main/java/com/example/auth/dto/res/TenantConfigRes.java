package com.example.auth.dto.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 租户配置
 *
 * @author Leo
 * @since 2020.08.01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(description = "租户配置")
public class TenantConfigRes implements Serializable {

	private static final long serialVersionUID = 6739280412020839652L;

	@ApiModelProperty(value = "平台域名")
	private String domain;

	@ApiModelProperty(value = "租户ID")
	private String tenantId;

	@ApiModelProperty(value = "租户描述")
	private String descriptions;

	@ApiModelProperty(value = "过期时间")
	private LocalDateTime expirationTime;
}
