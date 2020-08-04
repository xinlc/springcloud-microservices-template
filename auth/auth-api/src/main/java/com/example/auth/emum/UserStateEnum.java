package com.example.auth.emum;

/**
 * 用户状态枚举类
 *
 * @author Leo
 * @since 2020.08.01
 */
public enum UserStateEnum {

	/**
	 * 有效
	 */
	VALID(1),
	/**
	 * 锁定
	 */
	LOCK(2),
	/**
	 * 禁用
	 */
	FORBID(3),
	/**
	 * 删除
	 */
	DELETE(4),
	/**
	 * 待审核
	 */
	UNAUDITED(5),
	/**
	 * 通过
	 */
	PASS(6),
	/**
	 * 驳回
	 */
	REJECT(7);

	private Integer value;

	UserStateEnum(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}

	public static UserStateEnum getUserState(Integer value) {
		for (UserStateEnum userState : UserStateEnum.values()) {
			if (userState.value.equals(value)) {
				return userState;
			}
		}
		return null;
	}

}
