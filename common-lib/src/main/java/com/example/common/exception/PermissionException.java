package com.example.common.exception;

import com.example.common.api.ApiResultType;

/**
 * 没有访问权限
 *
 * @author Leo
 * @date 2020.05.28
 */
public class PermissionException extends BusinessException {

	private static final long serialVersionUID = -752845633562495205L;

	public PermissionException() {
		super(ApiResultType.GATEWAY_UNAUTHORIZED.getCode());
	}
}
