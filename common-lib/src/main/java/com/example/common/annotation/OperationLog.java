package com.example.common.annotation;

import com.example.common.emum.OpBusinessTypeEnum;
import com.example.common.emum.OperatorTypeEnum;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 操作日志记录注解
 *
 * @author Leo
 * @date 2020.04.12
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperationLog {
	/**
	 * 模块
	 */
	public String title() default "";

	/**
	 * 功能
	 */
	public OpBusinessTypeEnum businessType() default OpBusinessTypeEnum.OTHER;

	/**
	 * 操作人类别
	 */
	public OperatorTypeEnum operatorType() default OperatorTypeEnum.OTHER;

	/**
	 * 是否保存请求的参数
	 */
	public boolean isSaveRequestData() default true;
}
