package com.example.common.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * @author Leo
 * @date 2020.02.28
 */
public final class OkHttpUtil {

	/**
	 * 由于 OkHttp header 中的 value 不支持 null, \n 和 中文这样的特殊字符,所以这里
	 * 会首先替换 \n ,然后使用 OkHttp 的校验方式,校验不通过的话,就返回 encode 后的字符串
	 *
	 * @param value
	 * @return
	 */
	public static String getValueEncoded(String value) {
		if (value == null) return "null";
		String newValue = value.replace("\n", "");
		for (int i = 0, length = newValue.length(); i < length; i++) {
			char c = newValue.charAt(i);
			if (c <= '\u001f' || c >= '\u007f') {
				try {
					return URLEncoder.encode(newValue, "UTF-8");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return newValue;
	}

	/**
	 * 由于 OkHttp header 中的 value 不支持 null, \n 和 中文这样的特殊字符
	 * 上游传输时进行了编码，获取需要解码
	 *
	 * @param value
	 * @return
	 */
	public static String getValueDecode(String value) {
		if ("null".equals(value)) return null;
		try {
			return URLDecoder.decode(value, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
}
