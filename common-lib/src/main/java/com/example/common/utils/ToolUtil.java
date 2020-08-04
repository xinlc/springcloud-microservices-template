package com.example.common.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 工具包
 * 无法归类的通用函数，放在该类中
 *
 * @author Leo
 * @date 2020.04.12
 */
public class ToolUtil {
	/**
	 * 获取异常的具体信息
	 */
	public static String getExceptionMsg(Throwable e) {
		StringWriter sw = new StringWriter();
		try {
			e.printStackTrace(new PrintWriter(sw));
		} finally {
			try {
				sw.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return sw.getBuffer().toString().replaceAll("\\$", "T");
	}
}
