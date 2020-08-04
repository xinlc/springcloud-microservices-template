package com.example.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.function.Supplier;

/**
 * 断言工具
 *
 * @author Leo
 * @date 2020.04.11
 */
public class AssertUtil {
	private AssertUtil() {
	}

	public static void notEmpty(String string, String message) {
		if (StringUtils.isEmpty(string)) {
			throw new IllegalArgumentException(message);
		}
	}

	public static void notEmpty(String string, Supplier<String> messageSupplier) {
		notEmpty(string, messageSupplier.get());
	}

	public static <T> void notNull(T object, String message) {
		if (object == null) {
			throw new IllegalArgumentException(message);
		}
	}

	public static <T> void notNull(T object, Supplier<String> messageSupplier) {
		notNull(object, messageSupplier.get());
	}

	public static <T> void notEmpty(T[] array, String message) {
		if (array == null || array.length == 0) {
			throw new IllegalArgumentException(message);
		}
	}

	public static <T> void notEmpty(T[] array, Supplier<String> messageSupplier) {
		notEmpty(array, messageSupplier.get());
	}

	public static void notEmpty(Collection<?> collection, String message) {
		if (CollectionUtil.isEmpty(collection)) {
			throw new IllegalArgumentException(message);
		}
	}

	public static void assertState(boolean condition, String message) {
		if (!condition) {
			throw new IllegalStateException(message);
		}
	}

	public static void state(boolean expression, String message) {
		if (!expression) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * 断言一个boolean表达式，用于需要大量拼接字符串以及一些其他操作等
	 *
	 * @param expression boolean表达式
	 * @param supplier   msg生产者
	 */
	public static void state(boolean expression, Supplier<String> supplier) {
		if (!expression) {
			throw new IllegalArgumentException(supplier.get());
		}
	}
}
