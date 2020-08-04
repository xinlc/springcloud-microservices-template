package com.example.common.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 分布式锁
 *
 * @author Leo
 * @date 2020.04.11
 */
public class RedisDistributedLock {

	private static final Logger logger = LoggerFactory.getLogger(RedisDistributedLock.class);

	private StringRedisTemplate redisTemplate;

	/**
	 * 默认请求锁的超时时间(ms 毫秒)
	 */
	private static final long TIME_OUT = 100;

	/**
	 * 默认锁的有效时间(s)
	 */
	public static final int EXPIRE = 60;

	/**
	 * 锁标志对应的key
	 */
	private String lockKey;

	/**
	 * 锁对应的值
	 */
	private String lockValue;

	/**
	 * 锁的有效时间(s)
	 */
	private int expireTime = EXPIRE;

	/**
	 * 请求锁的超时时间(ms)
	 */
	private long timeOut = TIME_OUT;

	/**
	 * 锁标记
	 */
	private volatile boolean locked = false;

	/**
	 * 解锁 Lua 脚本
	 */
	public static final String UNLOCK_LUA;

	static {
		// 拼接 Lua 代码，保证释放锁的原子性
		StringBuilder sb = new StringBuilder();
		sb.append("if redis.call(\"get\",KEYS[1]) == ARGV[1] ");
		sb.append("then ");
		sb.append("    return redis.call(\"del\",KEYS[1]) ");
		sb.append("else ");
		sb.append("    return 0 ");
		sb.append("end ");
		UNLOCK_LUA = sb.toString();
	}

	private final Random random = new Random();

	/**
	 * 使用默认的锁过期时间和请求锁的超时时间
	 *
	 * @param redisTemplate
	 * @param lockKey       锁的key（Redis的Key）
	 */
	public RedisDistributedLock(StringRedisTemplate redisTemplate, String lockKey) {
		this.redisTemplate = redisTemplate;
		this.lockKey = lockKey + "_lock";
	}

	/**
	 * 使用默认的请求锁的超时时间，指定锁的过期时间
	 *
	 * @param redisTemplate
	 * @param lockKey       锁的key（Redis的Key）
	 * @param expireTime    锁的过期时间(单位：秒)
	 */
	public RedisDistributedLock(StringRedisTemplate redisTemplate, String lockKey, int expireTime) {
		this(redisTemplate, lockKey);
		this.expireTime = expireTime;
	}

	/**
	 * 使用默认的锁的过期时间，指定请求锁的超时时间
	 *
	 * @param redisTemplate
	 * @param lockKey       锁的key（Redis的Key）
	 * @param timeOut       请求锁的超时时间(单位：毫秒)
	 */
	public RedisDistributedLock(StringRedisTemplate redisTemplate, String lockKey, long timeOut) {
		this(redisTemplate, lockKey);
		this.timeOut = timeOut;
	}

	/**
	 * 锁的过期时间和请求锁的超时时间都是用指定的值
	 *
	 * @param redisTemplate
	 * @param lockKey       锁的key（Redis的Key）
	 * @param expireTime    锁的过期时间(单位：秒)
	 * @param timeOut       请求锁的超时时间(单位：毫秒)
	 */
	public RedisDistributedLock(StringRedisTemplate redisTemplate, String lockKey, int expireTime, long timeOut) {
		this(redisTemplate, lockKey, expireTime);
		this.timeOut = timeOut;
	}

	/**
	 * 尝试获取锁 立即返回
	 *
	 * @return 是否成功获得锁
	 */
	public boolean tryLock() {
		String lockValue = UUID.randomUUID().toString();
		Boolean result = redisTemplate.opsForValue().setIfAbsent(lockKey, lockValue, expireTime, TimeUnit.MILLISECONDS);
		locked = null != result && result;
		return locked;
	}

	/**
	 * 尝试获取锁
	 *
	 * @return 是否成功获得锁
	 */
	public boolean acquire() {
		// 生成随机值
		String lockValue = UUID.randomUUID().toString();

		// 请求锁超时时间，纳秒
		long timeout = timeOut * 1000000;

		// 系统当前时间，纳秒
		long beginTime = System.nanoTime();

		while ((System.nanoTime() - beginTime) < timeout) {
			Boolean result = redisTemplate.opsForValue().setIfAbsent(lockKey, lockValue, expireTime, TimeUnit.MILLISECONDS);
			if (null != result && result) {
				locked = true;
				// 上锁成功结束请求
				return locked;
			}

			// 每次请求等待一段时间
			seleep(10, 50000);
		}
		return locked;
	}

	/**
	 * 以阻塞方式的获取锁
	 *
	 * @return 是否成功获得锁
	 */
	public boolean lockBlock() {
		lockValue = UUID.randomUUID().toString();
		while (true) {
			Boolean result = redisTemplate.opsForValue().setIfAbsent(lockKey, lockValue, expireTime, TimeUnit.MILLISECONDS);
			if (locked = null != result && result) {
				locked = true;
				return locked;
			}

			// 每次请求等待一段时间
			seleep(10, 50000);
		}
	}

	/**
	 * 释放锁
	 */
	public void releaseLock() {
		List<String> keys = Collections.singletonList(lockKey);
		redisTemplate.execute(new DefaultRedisScript<Object>(UNLOCK_LUA, Object.class), keys, lockValue);
	}

	/**
	 * 线程等待
	 *
	 * @param millis 毫秒
	 * @param nanos  纳秒
	 */
	private void seleep(long millis, int nanos) {
		try {
			Thread.sleep(millis, random.nextInt(nanos));
		} catch (InterruptedException e) {
			logger.info("获取分布式锁休眠被中断：", e);
		}
	}

	/**
	 * 获取 value
	 */
	public String getLockValue() {
		return redisTemplate.opsForValue().get(lockKey);
	}

	/**
	 * 获取锁状态
	 */
	public boolean isLock() {
		return locked;
	}

	public int getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(int expireTime) {
		this.expireTime = expireTime;
	}

	public long getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(long timeOut) {
		this.timeOut = timeOut;
	}
}
