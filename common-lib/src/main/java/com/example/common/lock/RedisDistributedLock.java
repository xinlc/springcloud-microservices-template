package com.example.common.lock;

import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.util.Collections;
import java.util.List;

/**
 * Redis 分布式锁实现类
 *
 * @author Leo
 * @since 2020-01-17
 */
public class RedisDistributedLock extends DistributedLock {

	private RedisOperations<String, String> operations;
	private String key;
	private String value;

	/* 解锁 Lua 脚本 */
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

	/**
	 * @param operations
	 * @param key
	 * @param value
	 */
	public RedisDistributedLock(RedisOperations<String, String> operations, String key, String value) {
		this.operations = operations;
		this.key = key;
		this.value = value;
	}

	@Override
	public void release() {
		// 释放锁
		List<String> keys = Collections.singletonList(key);
		operations.execute(new DefaultRedisScript<Object>(UNLOCK_LUA, Object.class), keys, value);
	}

	@Override
	public String toString() {
		return "RedisDistributedLock [key=" + key + ", value=" + value + "]";
	}

}
