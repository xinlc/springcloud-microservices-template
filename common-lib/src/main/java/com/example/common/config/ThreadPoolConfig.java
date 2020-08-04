package com.example.common.config;

import com.example.common.async.ContextCopyingDecorator;
import com.example.common.utils.ThreadsUtil;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池配置
 *
 * @author Leo
 * @date 2020.04.12
 */
@EnableAsync
@Configuration
public class ThreadPoolConfig {
	/**
	 * 异步任务
	 */
	public static final String TASK_EXECUTOR_NAME = "taskExecutor";

	/**
	 * 异步调度任务，支持复制上下文
	 */
	public static final String ASYNC_EXECUTOR_NAME = "asyncExecutor";

	/**
	 * 执行周期性或定时任务
	 */
	public static final String SCHEDULED_EXECUTOR_SERVICE_NAME = "scheduledExecutorService";

	// 核心线程池大小
	private int corePoolSize = 20;

	// 最大可创建的线程数
	private int maxPoolSize = 100;

	// 队列最大长度
	private int queueCapacity = 200;

	// 线程池维护线程所允许的空闲时间
	private int keepAliveSeconds = 120;

	// 线程池中任务的等待时间，超过这个时间还没有销毁，就强制销毁
	private int awaitTerminationSeconds = 60;

	/**
	 * 异步任务
	 */
	@Bean(name = TASK_EXECUTOR_NAME)
	public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(corePoolSize);
		executor.setMaxPoolSize(maxPoolSize);
		executor.setQueueCapacity(queueCapacity);
		executor.setKeepAliveSeconds(keepAliveSeconds);
		executor.setAwaitTerminationSeconds(awaitTerminationSeconds);
		// 线程池关闭的时候等待所有任务都完成再继续销毁其他的 Bean
		executor.setWaitForTasksToCompleteOnShutdown(true);
		executor.setAllowCoreThreadTimeOut(true);
		executor.setThreadNamePrefix("TaskExecutor-");
		// 线程池对拒绝任务(无线程可用)的处理策略：交给当前线程去执行
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		executor.initialize();
		return executor;
	}

	/**
	 * 异步调度任务，支持复制上下文
	 * 场景：异步通知下游服务
	 */
	@Bean(name = ASYNC_EXECUTOR_NAME)
	public Executor asyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(corePoolSize);
		executor.setMaxPoolSize(maxPoolSize);
		executor.setQueueCapacity(queueCapacity);
		executor.setKeepAliveSeconds(keepAliveSeconds);
		executor.setAwaitTerminationSeconds(awaitTerminationSeconds);
		// 线程池关闭的时候等待所有任务都完成再继续销毁其他的 Bean
		executor.setWaitForTasksToCompleteOnShutdown(true);
		executor.setAllowCoreThreadTimeOut(true);
		executor.setThreadNamePrefix("AsyncExecutor-");
		// 线程池对拒绝任务(无线程可用)的处理策略：直接抛出异常
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
		// for passing in request scope context
		executor.setTaskDecorator(new ContextCopyingDecorator());
		executor.initialize();
		return executor;
	}

	/**
	 * 执行周期性或定时任务
	 */
	@Bean(name = SCHEDULED_EXECUTOR_SERVICE_NAME)
	public ScheduledExecutorService scheduledExecutorService() {
		return new ScheduledThreadPoolExecutor(corePoolSize,
				new BasicThreadFactory.Builder().namingPattern("schedule-pool-%d").daemon(true).build()) {
			@Override
			protected void afterExecute(Runnable r, Throwable t) {
				super.afterExecute(r, t);
				ThreadsUtil.printException(r, t);
			}
		};
	}
}
