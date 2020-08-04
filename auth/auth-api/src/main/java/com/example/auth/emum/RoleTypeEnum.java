package com.example.auth.emum;

/**
 * 角色类型
 *
 * @author Leo
 * @since 2020.08.01
 */
public enum RoleTypeEnum {
	// CRM 超级管理员
	CRM_ADMIN(99),

	// 平台管理员
	PLATFORM_ADMIN(98),

	// 普通角色
	NORMAL_ROLE(0);

	private Integer value;

	RoleTypeEnum(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}

	public static RoleTypeEnum getValue(Integer value) {
		for (RoleTypeEnum type : RoleTypeEnum.values()) {
			if (type.value.equals(value)) {
				return type;
			}
		}
		return RoleTypeEnum.NORMAL_ROLE;
	}
}

