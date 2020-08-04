package com.example.auth.emum;

/**
 * 登陆类型枚举类
 *
 * @author Leo
 * @since 2020.08.01
 */
public enum IdentityTypeEnum {

	/**
	 * 用户名
	 */
	USERNAME(1),
	/**
	 * 手机号码
	 */
	MOBILE(2),
	/**
	 * 邮箱
	 */
	EMAIL(3),
	/**
	 * 身份证号码
	 */
	ID_CARD(4),
	/**
	 * 社会信用代码
	 */
	UNIFIED_CODE(5),
	/**
	 * 第三方平台
	 */
	THIRD_PARTY(6);

	private Integer value;

	IdentityTypeEnum(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}

	public static IdentityTypeEnum getIdentityType(Integer value) {
		for (IdentityTypeEnum identityType : IdentityTypeEnum.values()) {
			if (identityType.value.equals(value)) {
				return identityType;
			}
		}
		return null;
	}

}
