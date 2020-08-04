package com.example.auth.emum;

/**
 * @author Leo
 * @since 2020.08.01
 */
public enum SystemRoleEnum {
	/**
	 * 系统超级管理员
	 */
	SYSTEM_SUPER_ADMIN(99, "系统超级管理员", "拥有整个系统的最高权限，不可被操作"),
	/**
	 * 平台超级管理员
	 */
	PLATFORM_SUPER_ADMIN(98, "平台超级管理员", "用户平台最高权限");

	public Integer code;
	public String title;
	public String description;

	public Integer getCode() {
		return code;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	SystemRoleEnum(Integer code, String title, String description) {
		this.code = code;
		this.title = title;
		this.description = description;
	}

	/**
	 * 通过code取得Msg信息
	 *
	 * @param code 角色类别
	 * @return Msg信息
	 */
	public static String getMsgByCode(Integer code) {
		for (SystemRoleEnum result : SystemRoleEnum.values()) {
			if (code.equals(result.getCode())) {
				return result.getTitle();
			}
		}
		return null;
	}

	/**
	 * 通过code取得描述信息
	 *
	 * @param code 角色类别
	 * @return 描述信息
	 */
	public static String getDesByCode(Integer code) {
		for (SystemRoleEnum result : SystemRoleEnum.values()) {
			if (code.equals(result.getCode())) {
				return result.getDescription();
			}
		}
		return null;
	}
}
