package com.example.common.annotation;

import java.lang.annotation.*;

/**
 * 绑定当前登录的用户
 *
 * @author Leo
 * @since 2020.08.01
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CurrentUser {

	/**
	 * 当前用户在request中的名字
	 *
	 * @return
	 */
	String value() default "user";

}
