package com.example.common.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

/**
 * 确保应用退出时能关闭后台线程
 *
 * @author Leo
 * @date 2020.04.12
 */
@Component
public class ShutdownManager {

	private static final Logger logger = LoggerFactory.getLogger("sys-user");

	@PreDestroy
	public void destroy() {
		shutdownAsyncManager();
	}

	/**
	 * 停止异步执行任务
	 */
	private void shutdownAsyncManager() {
		try {
			logger.info("====关闭后台任务线程池====");
			// 如果关闭前没有调用过 AsyncManager.me()，那这里会报 java.lang.ExceptionInInitializerError，不用理会。
			AsyncManager.me().shutdown();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
}
