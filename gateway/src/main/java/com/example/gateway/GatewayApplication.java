package com.example.gateway;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * API 网关
 * <p>
 * 服务唯一入口, 所有的客户端和消费端都通过统一的网关接入微服务，在网关层处理所有的非业务功能。如：身份验证、监控、限流、熔断、负载均衡等。
 *
 * @author Leo
 * @date 2020.02.14
 */
@SpringBootApplication(exclude = {
		MybatisPlusAutoConfiguration.class,
		DruidDataSourceAutoConfigure.class,
		DataSourceAutoConfiguration.class,
		DataSourceTransactionManagerAutoConfiguration.class,
		HibernateJpaAutoConfiguration.class
})
@ComponentScan(basePackages = {"com.example.**"})
@EnableFeignClients(basePackages = {"com.example.auth", "com.example.gateway.provider"})
@EnableMethodCache(basePackages = "com.example.gateway.service")
@EnableCreateCacheAnnotation
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);

//		ConfigurableApplicationContext applicationContext = SpringApplication.run(GatewayApplication.class, args);
//		String userName = applicationContext.getEnvironment().getProperty("user.name");
//		String userAge = applicationContext.getEnvironment().getProperty("user.age");
//		System.err.println("user name :"+userName+"; age: "+userAge);
//		String aa = applicationContext.getEnvironment().getProperty("spring.cloud.sentinel.transport.dashboard");
//		System.err.println("user name :"+aa);
	}

}
