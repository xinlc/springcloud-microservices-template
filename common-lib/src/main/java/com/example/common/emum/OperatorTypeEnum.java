package com.example.common.emum;

/**
 * 操作人类别
 *
 * @author Leo
 * @date 2020.04.12
 */
public enum OperatorTypeEnum {
	/**
	 * 其它
	 */
	OTHER(0),

	/**
	 * 系统
	 */
	SYSTEM(1),

	/**
	 * 管理员
	 */
	ADMIN(2),

	/**
	 * 供应商用户
	 */
	SUPPLIER(3),

	/**
	 * 采购商用户
	 */
	PURCHASER(4);

	private Integer value;

	OperatorTypeEnum(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}

	public static OperatorTypeEnum valueOf(Integer value) {
		for (OperatorTypeEnum userState : OperatorTypeEnum.values()) {
			if (userState.value.equals(value)) {
				return userState;
			}
		}
		return null;
	}
}
