package com.example.common.context;


import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Spring ApplicationContext 的持有者, 可以用静态方法的方式获取 Spring 容器中的 Bean
 *
 * @author Leo
 * @date 2020.02.19
 */
@Component
@SuppressWarnings("all")
public class SpringContextHolder implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringContextHolder.applicationContext = applicationContext;
	}

	public static ApplicationContext getApplicationContext() {
		assertApplicationContext();
		return applicationContext;
	}

	public static <T> T getBean(String beanName) {
		assertApplicationContext();
		return (T) applicationContext.getBean(beanName);
	}

	public static <T> T getBean(Class<T> requiredType) {
		assertApplicationContext();
		return applicationContext.getBean(requiredType);
	}

	public static <T> List<T> getBeanOfType(Class<T> requiredType) {
		assertApplicationContext();
		Map<String, T> map = applicationContext.getBeansOfType(requiredType);
		return map == null ? null : new ArrayList<>(map.values());
	}

	public static void autowireBean(Object bean) {
		applicationContext.getAutowireCapableBeanFactory().autowireBean(bean);
	}

	private static void assertApplicationContext() {
		if (SpringContextHolder.applicationContext == null) {
			throw new RuntimeException("applicaitonContext 属性为 null，请检查是否注入了 SpringContextHolder!");
		}
	}

}
