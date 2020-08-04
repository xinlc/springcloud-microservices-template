package com.example.common.annotation;

import java.lang.annotation.*;

/**
 * 权限注解，用于检查权限
 * <p>
 * example: @RequiresPermissions({"ums:user", "ums:user:add"})
 *
 * @author Leo
 * @date 2020.05.28
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequiresPermissions {

	/**
	 * 权限值（如：ums:user:add）
	 */
	String[] value() default {};

}
