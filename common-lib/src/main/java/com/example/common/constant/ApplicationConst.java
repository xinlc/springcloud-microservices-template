package com.example.common.constant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class ApplicationConst {
	/**
	 * 日期格式化：yyyy-MM-dd HH:mm:ss
	 */
	public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	/**
	 * 多租户标识
	 */
	public static final String SYSTEM_TENANT_ID = "tenant_id";
	/**
	 * Redis存储Token前缀
	 */
	public static final String TOKEN_PREFIX = "auth.";
	/**
	 * Redis存储Token key 前缀
	 */
	public static final String TOKEN_KEY_PREFIX = "auth:token:key:user:id:";
	/**
	 * 多租户需要过滤的表
	 */
	public static final List<String> IGNORE_TENANT_TABLES = new ArrayList<>(Arrays.asList(
			"tenant_config",
			"cn_area",
			"ums_permission",
			"ums_menu",
			"ums_menu_permission",
			"ums_role_info_system",
			"ums_role_menu_system",
			"biz_system",
			"biz_resource",
			"biz_resource_category",
			"biz_plan",
			"biz_plan_resource",
			"sys_operation_log"
	));
}
