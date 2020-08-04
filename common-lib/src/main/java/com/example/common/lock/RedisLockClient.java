package com.example.common.lock;

import com.example.common.annotation.DistributedLockable;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * redis lock service
 *
 * @author Leo
 * @since 2020-01-17
 */
@Slf4j
public class RedisLockClient {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	/**
	 * 使用 AOP 获取锁
	 *
	 * @param <T>
	 * @param key         lock key
	 * @param handler     处理器
	 * @param timeout     超时时间
	 * @param autoUnlock  完成时是否自动解锁
	 * @param retries     重试次数
	 * @param waitingTime 重试间隔时间
	 * @param onFailure   获取锁失败时抛出的异常
	 * @return
	 */
	public <T> T tryLock(String key, LockHandler<T> handler, long timeout, boolean autoUnlock, int retries, long waitingTime,
						 Class<? extends RuntimeException> onFailure) throws Throwable {

		if (autoUnlock) {
			// 获取锁，完成后自动释放
			try (DistributedLock lock = this.acquire(key, timeout, retries, waitingTime);) {
				if (lock != null) {
					log.debug("get lock success, key: {}", key);
					return handler.handle();
				}
				log.debug("get lock fail, key: {}", key);
				if (null != onFailure && onFailure != DistributedLockable.NoException.class) {
					// 反射实例
					throw onFailure.newInstance();
				}
				return null;
			}
		} else {
			DistributedLock lock = this.acquire(key, timeout, retries, waitingTime);
			if (lock != null) {
				log.debug("get lock success, key: {}", key);
				return handler.handle();
			}
			log.debug("get lock fail, key: {}", key);
			if (null != onFailure && onFailure != DistributedLockable.NoException.class) {
				// 反射实例
				throw onFailure.newInstance();
			}
			return null;
		}
	}

	/**
	 * acquire distributed lock
	 *
	 * @param key         lock key
	 * @param timeout     超时时间
	 * @param retries     重试次数
	 * @param waitingTime 重试间隔时间
	 * @return
	 * @throws InterruptedException
	 */
	public DistributedLock acquire(String key, long timeout, int retries, long waitingTime) throws InterruptedException {

		// 锁值要保证唯一, 使用4位随机字符串+时间戳基本可满足需求 注: UUID.randomUUID()在高并发情况下性能不佳.
		final String value = RandomStringUtils.randomAlphanumeric(4) + System.currentTimeMillis();
		do {
			// 获取锁
			Boolean result = stringRedisTemplate.opsForValue().setIfAbsent(key, value, timeout, TimeUnit.MILLISECONDS);
			if (null != result && result) {
				return new RedisDistributedLock(stringRedisTemplate, key, value);
			}

			// 重试获取锁
			if (retries > NumberUtils.INTEGER_ZERO) {
				// 优化重试策略为订阅 Redis 事件: 订阅 Redis 事件可以进一步优化锁的性能, 可通过 wait + notifyAll 来替代 sleep
				TimeUnit.MILLISECONDS.sleep(waitingTime);
			}
			if (Thread.currentThread().isInterrupted()) {
				break;
			}
		} while (retries-- > NumberUtils.INTEGER_ZERO);

		return null;
	}


//	example
//	@DistributedLockable(
//			argNames = {"anyObject.id", "anyObject.name", "param1"},
//			timeout = 20, unit = TimeUnit.SECONDS,
//			onFailure = RuntimeException.class
//	)
//	public Long distributedLockableOnFaiFailure(AnyObject anyObject, String param1, Object param2, Long timeout) {
//		try {
//			TimeUnit.SECONDS.sleep(timeout);
//			log.info("distributed-lockable: " + System.nanoTime());
//		} catch (InterruptedException e) {
//		}
//		return System.nanoTime();
//	}
}
