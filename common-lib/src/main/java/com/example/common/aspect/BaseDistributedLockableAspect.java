package com.example.common.aspect;

import com.example.common.annotation.DistributedLockable;
import com.example.common.lock.KeyGenerator;
import com.example.common.lock.RedisLockClient;
import org.aspectj.lang.ProceedingJoinPoint;

import javax.annotation.Resource;

/**
 * 分布式锁 AOP
 *
 * @author Leo
 * @since 2020-01-17
 */
public abstract class BaseDistributedLockableAspect implements KeyGenerator {

//	@Autowired

	@Resource
	private RedisLockClient redisLockClient;


	/**
	 * 切入点
	 * {@link DistributedLockable}
	 */
	public void distributedLockable() {
	}

	/**
	 * 切面
	 *
	 * @param joinPoint
	 * @param lockable
	 * @return
	 * @throws Throwable
	 */
	public Object around(ProceedingJoinPoint joinPoint, DistributedLockable lockable) throws Throwable {
		// 生成 key
		final String key = this.generate(joinPoint, lockable.prefix(), lockable.argNames(), lockable.argsAssociated()).toString();

		// 获取锁
		Object result = redisLockClient.tryLock(
				key,
				joinPoint::proceed,
				lockable.unit().toMillis(lockable.timeout()),
				lockable.autoUnlock(),
				lockable.retries(),
				lockable.unit().toMillis(lockable.waitingTime()),
				lockable.onFailure()
		);
		return result;
	}
}
