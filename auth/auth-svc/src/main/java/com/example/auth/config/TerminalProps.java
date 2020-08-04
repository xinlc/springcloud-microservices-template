package com.example.auth.config;

import lombok.Getter;
import lombok.Setter;

/**
 * 系统终端访问配置信息
 *
 * @author Leo
 * @since 2020.08.01
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
	 * JWT 信息
	 */
	private String tokenHead;
	private String secret;
	private String payloadSecret;
	private Long expiration;
	private int refreshInterval;
}
