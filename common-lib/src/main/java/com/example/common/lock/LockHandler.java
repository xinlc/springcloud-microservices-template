package com.example.common.lock;

/**
 * 分布式锁 AOP 处理器
 *
 * @author Leo
 * @since 2020-01-17
 */
@FunctionalInterface
public interface LockHandler<T> {

	/**
	 * @return
	 * @throws Throwable
	 */
	T handle() throws Throwable;
}
