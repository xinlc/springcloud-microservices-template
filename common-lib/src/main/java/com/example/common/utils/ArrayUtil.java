package com.example.common.utils;

import java.util.Objects;

/**
 * Array 工具
 *
 * @author Leo
 * @date 2020.04.11
 */
public class ArrayUtil {

	/**
	 * 判断数组中是否存在指定元素
	 *
	 * @param arrays 数组
	 * @param val    校验的元素
	 * @param <T>    数组原始类型
	 * @return 是否存在
	 */
	public static <T> boolean contains(T[] arrays, T val) {
		if (arrays == null) {
			return false;
		}
		for (T t : arrays) {
			if (Objects.equals(t, val)) {
				return true;
			}
		}
		return false;
	}
}
