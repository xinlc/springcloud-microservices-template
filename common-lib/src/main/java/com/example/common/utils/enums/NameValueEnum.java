package com.example.common.utils.enums;

/**
 * 带有枚举值以及枚举名称的枚举接口(可使用{@link EnumUtil}中的方法)
 *
 * @author Leo
 * @date 2020.04.11
 */
public interface NameValueEnum<T> extends ValueEnum<T> {
	/**
	 * 获取枚举名称
	 *
	 * @return 枚举名
	 */
	String getName();
}
