package com.example.common.utils.equator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 对象比对器字段注解，用于标记字段信息
 *
 * @author Leo
 * @date 2020.04.30
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD})
public @interface EquatorField {

	/**
	 * 配置字段顺序
	 */
	int ordinal() default 0;

	/**
	 * 描述
	 */
	String description() default "";
}
