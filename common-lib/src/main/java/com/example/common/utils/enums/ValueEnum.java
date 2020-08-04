package com.example.common.utils.enums;

/**
 * 最简单的枚举类，即只含value的枚举（实现此接口可使用{@link EnumUtil}中的方法）
 *
 * @author Leo
 * @date 2020.04.11
 */
public interface ValueEnum<T> {

	/**
	 * 获取枚举值
	 *
	 * @return 枚举值
	 */
	T getValue();
}
