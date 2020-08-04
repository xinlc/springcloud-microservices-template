package com.example.gateway.context;

import com.example.common.bo.UserInfoBo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * api 请求上下文
 *
 * @author Leo
 * @date 2020.02.24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiContext implements Serializable {

	private static final long serialVersionUID = -2678923061075126428L;

	/**
	 * 当前用户信息
	 */
	private UserInfoBo userInfoBo;

	/**
	 * 权限信息
	 */
	private String authz;

	/**
	 * 租户 id
	 */
	private String tenantId;

	/**
	 * 系统ID
	 */
	private String systemId;

}
