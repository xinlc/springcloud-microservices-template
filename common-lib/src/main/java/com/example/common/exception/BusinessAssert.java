package com.example.common.exception;

import com.example.common.utils.CollectionUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * 业务异常断言
 *
 * @author Leo
 * @date 2020.02.17
 */
public class BusinessAssert {
	/**
	 * 断言对象不为空
	 *
	 * @param object 对象
	 * @param msg    不满足断言的异常信息
	 */
	public static void notNull(Object object, String msg) {
		state(object != null, msg);
	}

	public static void notNull(Object object, Supplier<String> supplier) {
		state(object != null, supplier);
	}

	/**
	 * 断言对象为空
	 *
	 * @param object 对象
	 * @param msg    不满足断言的异常信息
	 */
	public static void isNull(Object object, String msg) {
		state(object == null, msg);
	}

	public static void isNull(Object object, Supplier<String> supplier) {
		state(object == null, supplier);
	}


	/**
	 * 断言字符串不为空
	 *
	 * @param str 字符串
	 * @param msg 不满足断言的异常信息
	 */
	public static void notEmpty(String str, String msg) {
		state(!StringUtils.isEmpty(str), msg);
	}

	public static void notEmpty(String str, Supplier<String> supplier) {
		state(!StringUtils.isEmpty(str), supplier);
	}

	/**
	 * 断言集合不为空
	 *
	 * @param collection 集合
	 * @param msg        不满足断言的异常信息
	 */
	public static void notEmpty(Collection<?> collection, String msg) {
		state(!CollectionUtil.isEmpty(collection), msg);
	}

	/**
	 * 断言集合为空
	 *
	 * @param collection 集合
	 * @param msg        不满足断言的异常信息
	 */
	public static void empty(Collection<?> collection, String msg) {
		state(CollectionUtil.isEmpty(collection), msg);
	}

	/**
	 * 断言两个对象必须相等
	 *
	 * @param o1   对象1
	 * @param o2   对象2
	 * @param msg  错误消息
	 */
	public static void equals(Object o1, Object o2, String msg) {
		state(Objects.equals(o1, o2), msg);
	}

	/**
	 * 断言两个对象必须相等
	 *
	 * @param o1   对象1
	 * @param o2   对象2
	 * @param msgSupplier  错误消息提供器
	 */
	public static void equals(Object o1, Object o2, Supplier<String> msgSupplier) {
		state(Objects.equals(o1, o2), msgSupplier);
	}

	/**
	 * 断言一个boolean表达式
	 *
	 * @param expression boolean表达式
	 * @param message    不满足断言的异常信息
	 */
	public static void state(boolean expression, String message) {
		if (!expression) {
			throw new BusinessException(message);
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
			throw new BusinessException(supplier.get());
		}
	}
}
