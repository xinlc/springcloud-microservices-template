package com.example.gateway.config;

import lombok.Data;

@Data
public class SwaggerResourceProps {
	/**
	 * 文档名
	 */
	private String name;

	/**
	 * 文档所在服务地址
	 */
	private String location;

	/**
	 * 文档版本
	 */
	private String version = "2.0";

}
