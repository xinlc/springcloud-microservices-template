package com.example.common.emum;

/**
 * 日志类型
 *
 * @author Leo
 * @date 2020.04.12
 */
public enum LogTypeEnum {
	LOGIN(0, "登录日志"),

	LOGIN_FAIL(1, "登录失败日志"),

	EXIT(2, "退出日志"),

	EXCEPTION(3, "异常日志"),

	BUSINESS(4, "业务日志");

	private Integer value;
	private String message;

	LogTypeEnum(Integer value, String message) {
		this.value = value;
		this.message = message;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
