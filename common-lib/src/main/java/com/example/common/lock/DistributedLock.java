package com.example.common.lock;

import lombok.extern.slf4j.Slf4j;

/**
 * 分布式锁抽象类
 * 实现 AutoCloseable 接口, 可使用 try-with-resource 方便地完成自动解锁
 *
 * @author Leo
 * @since 2020-01-17
 */
@Slf4j
public abstract class DistributedLock implements AutoCloseable {

	/**
	 * release lock
	 */
	public abstract void release();

	/**
	 * @see AutoCloseable#close()
	 */
	@Override
	public void close() throws Exception {

		log.debug("distributed lock close , {}", this.toString());

		this.release();
	}
}
