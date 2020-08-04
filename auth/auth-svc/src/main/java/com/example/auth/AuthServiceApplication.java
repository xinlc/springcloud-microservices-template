package com.example.auth;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 授权，鉴权服务
 *
 * @author Leo
 * @since 2020.08.01
 */
@SpringBootApplication
@ServletComponentScan
@MapperScan(basePackages = {"com.example.dao.mapper", "com.example.auth.mapper"})
@ComponentScan(basePackages = {"com.example.**"})
@EnableMethodCache(basePackages = "com.example.auth.service")
@EnableCreateCacheAnnotation
public class AuthServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthServiceApplication.class, args);
	}

}
