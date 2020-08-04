package com.example.common.utils;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.IOException;

/**
 * JSON 工具
 *
 * @author Leo
 * @date 2020.04.11
 */
public class JsonUtil {

	private static final ObjectMapper objectMapper = new ObjectMapper();
	private static final ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();

	private JsonUtil() {
	}

	/**
	 * 将对象转为json串
	 *
	 * @param object 对象
	 * @return json
	 */
	public static String toJSONString(Object object) {
		return JSON.toJSONString(object);
	}

	/**
	 * 将json字符串转为对象
	 *
	 * @param json  json
	 * @param clazz 对象class
	 * @param <T>   对象实际类型
	 * @return 对象
	 */
	public static <T> T parseObject(String json, Class<T> clazz) {
		return JSON.parseObject(json, clazz);
	}


	/**
	 * marshal
	 *
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public static String marshal(Object value) throws Exception {
		try {
			return objectWriter.writeValueAsString(value);
		} catch (IOException e) {
			throw new Exception(e);
		}
	}
}
