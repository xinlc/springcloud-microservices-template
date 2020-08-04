package com.example.common.annotation;

import com.example.common.config.RedisConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;


/**
 * 分布式锁
 *
 * @author Leo
 * @since 2020-01-17
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(RedisConfig.class)
public @interface EnableDistributedLock {

}
