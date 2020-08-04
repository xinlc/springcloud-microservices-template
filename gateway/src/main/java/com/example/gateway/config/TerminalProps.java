package com.example.gateway.config;

import lombok.Getter;
import lombok.Setter;

/**
 * 系统终端访问配置信息
 *
 * @author Leo
 * @date 2020.02.28
 */
@Getter
@Setter
public class TerminalProps {

	/**
	 * 系统名称：供应商端
	 */
	private String name;

	/**
	 * 系统 ID：1 -> 对应 biz_system.id
	 */
	private String systemId;

	/**
	 * 访问标识：/supplier-api/**
	 */
	private String accessFlag;

	/**
	 * cookie token key
	 */
	private String cookieTokenKey;
}
