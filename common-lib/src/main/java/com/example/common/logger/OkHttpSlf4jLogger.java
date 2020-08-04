package com.example.common.logger;

import com.example.common.interceptor.OkHttpLogInterceptor;
import lombok.extern.slf4j.Slf4j;

/**
 * OkHttp Slf4j logger
 *
 * @author Leo
 * @date 2020.02.28
 */
@Slf4j
public class OkHttpSlf4jLogger implements OkHttpLogInterceptor.Logger {
	@Override
	public void log(String message) {
		log.info(message);
	}
}
