package com.example.common.utils;

import java.util.Map;
import java.util.function.Function;

/**
 * {}占位符解析工具
 *
 * @author Leo
 * @date 2020.04.11
 */
public class BracePlaceholderUtil {
	/**
	 * 花括号占位符解析器
	 */
	private static PlaceholderResolverUtil resolver = PlaceholderResolverUtil.getResolver("{", "}");

	public static String resolve(String content, Object... objs) {
		return resolver.resolve(content, objs);
	}

	public static String resolveByMap(String content, Map<String, Object> map) {
		return resolver.resolveByMap(content, map);
	}

	public static String resolveByObject(String content, Object object) {
		return resolver.resolveByObject(content, object);
	}

	public static String resolveByRule(String content, Function<String, String> rule) {
		return resolver.resolveByRule(content, rule);
	}
}
