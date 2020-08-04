package com.example.common.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.example.common.interceptor.ApiIdempotentInterceptor;
import com.example.common.interceptor.ApiInterceptor;
import com.example.common.interceptor.ServerProtectInterceptor;
import com.example.common.resolvers.CurrentUserResolver;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Web MVC 配置
 *
 * @author Leo
 * @date 2020.04.15
 */
@Configuration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class WebMvcConfig extends WebMvcConfigurationSupport {

	/**
	 * 配置消息转换器，使用 fastJson 代替 jackson
	 */
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();

		// 序列化配置
		FastJsonConfig config = new FastJsonConfig();
		config.setSerializerFeatures(SerializerFeature.QuoteFieldNames,
				SerializerFeature.WriteEnumUsingToString,
				// 输出值为null的字段
				SerializerFeature.WriteMapNullValue,
				// 时期格式格式化为 yyyy-MM-dd HH:mm:ss
				SerializerFeature.WriteDateUseDateFormat,
				// 禁用循环引用
				SerializerFeature.DisableCircularReferenceDetect);
		fastJsonHttpMessageConverter.setFastJsonConfig(config);

		// 添加支持的MediaTypes;不添加时默认为*/*,也就是默认支持全部
		// 但是MappingJackson2HttpMessageConverter里面支持的MediaTypes为application/json
		List<MediaType> fastMediaTypes = new ArrayList<>();
		fastMediaTypes.add(MediaType.APPLICATION_JSON);
		fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
		fastJsonHttpMessageConverter.setSupportedMediaTypes(fastMediaTypes);

		converters.add(fastJsonHttpMessageConverter);
//		converters.add(new StringHttpMessageConverter(StandardCharsets.UTF_8));
		converters.add(new ByteArrayHttpMessageConverter());
		super.configureMessageConverters(converters);
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(currentUserResolver());
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(apiInterceptor());

		// 未完善
//        registry.addInterceptor(serverProtectInterceptor());
//        registry.addInterceptor(apiIdempotentInterceptor());
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// 解决 SWAGGER 404报错
		registry.addResourceHandler("/swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}

	@Bean
	public CurrentUserResolver currentUserResolver() {
		return new CurrentUserResolver();
	}

    @Bean
    public ApiInterceptor apiInterceptor() {
        return new ApiInterceptor();
    }

	/**
	 * 微服务防护拦截器
	 */
	@Bean
	public ServerProtectInterceptor serverProtectInterceptor() {
		return new ServerProtectInterceptor();
	}

	/**
	 * 接口幂等性拦截器
	 */
	@Bean
	public ApiIdempotentInterceptor apiIdempotentInterceptor() {
		return new ApiIdempotentInterceptor();
	}
}
