package com.example.common.config;

import com.example.common.emum.OkHttpLogLevelEnum;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.util.concurrent.TimeUnit;

/**
 * 配置 OKHttp
 *
 * @author Leo
 * @date 2020.02.17
 */
@Getter
@Setter
@RefreshScope
@ConfigurationProperties(prefix = "okhttp")
public class OkHttpProps {
	/**
	 * 最大连接数，默认：200
	 */
	private int maxConnections = 200;

	/**
	 * 连接存活时间，默认：900L
	 */
	private long timeToLive = 900L;

	/**
	 * 链接超时，默认：2L
	 */
	private long connectionTimeout = 2L;

	/**
	 * 读超时，默认：30L
	 */
	private long readTimeout = 30L;

	/**
	 * 写超时，默认：60L
	 */
	private long writeTimeout = 60L;

	/**
	 * 时间单位，默认：秒
	 */
	private TimeUnit timeUnit = TimeUnit.SECONDS;

	/**
	 * 是否支持重定向，默认：false
	 */
	private boolean followRedirects = false;

	/**
	 * 关闭证书校验
	 */
	private boolean disableSslValidation = true;

	/**
	 * 错误重连，默认: true
	 */
	private boolean retryOnConnectionFailure = true;

	/**
	 * 日志级别
	 */
	private OkHttpLogLevelEnum level = OkHttpLogLevelEnum.NONE;
}
