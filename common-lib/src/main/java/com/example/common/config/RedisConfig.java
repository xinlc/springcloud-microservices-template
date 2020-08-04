package com.example.common.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.common.lock.RedisLockClient;
import com.example.common.redis.FastJson2JsonRedisSerializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * redis 配置
 *
 * @author Leo
 * @date 2019/08/28
 */
@Configuration
public class RedisConfig implements ImportAware {

	/**
	 * 获取分布式锁客户端实例
	 *
	 * @return
	 * @author Leo
	 * @since 2020-01-17
	 */
	@Bean
	public RedisLockClient redisLockClient() {
		return new RedisLockClient();
	}

	/**
	 * 配置 string redis template
	 *
	 * @param factory
	 * @return
	 * @author Leo
	 * @since 2020-01-17
	 */
	@Bean
	@ConditionalOnMissingBean
	public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory factory) {

		StringRedisSerializer keySerializer = new StringRedisSerializer();
		RedisSerializer<?> serializer = new StringRedisSerializer();
		StringRedisTemplate template = new StringRedisTemplate();
		template.setConnectionFactory(factory);
		template.setKeySerializer(keySerializer);
		template.setHashKeySerializer(keySerializer);
		template.setValueSerializer(serializer);
		template.setHashValueSerializer(serializer);
		template.afterPropertiesSet();

		return template;

	}

	@Bean
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
		redisTemplate.setConnectionFactory(redisConnectionFactory);
		FastJson2JsonRedisSerializer fastJson2JsonRedisSerializer = new FastJson2JsonRedisSerializer(Object.class);
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		fastJson2JsonRedisSerializer.setObjectMapper(objectMapper);

		StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
		// key采用String的序列化方式
		redisTemplate.setKeySerializer(stringRedisSerializer);
		// string的value采用fastJson序列化方式
		redisTemplate.setValueSerializer(fastJson2JsonRedisSerializer);
		// hash的key也采用String的序列化方式
		redisTemplate.setHashKeySerializer(stringRedisSerializer);
		// hash的value采用fastJson序列化方式
		redisTemplate.setHashValueSerializer(fastJson2JsonRedisSerializer);
		redisTemplate.afterPropertiesSet();
		return redisTemplate;
	}

	/**
	 * (non-Javadoc)
	 *
	 * @author Leo
	 * @see ImportAware#setImportMetadata(AnnotationMetadata)
	 * @since 2020-01-17
	 */
	@Override
	public void setImportMetadata(AnnotationMetadata annotationMetadata) {

	}
}
