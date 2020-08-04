package com.example.common.aspect;

import com.example.common.annotation.RequiresPermissions;
import com.example.common.exception.PermissionException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * 权限校验 AOP
 *
 * @author Leo
 * @date 2020.05.28
 */
//@Aspect
//@Order(200)
public abstract class BasePermissionsAspect {

	private static final Logger log = LoggerFactory.getLogger(BasePermissionsAspect.class);

	/**
	 * 配置织入点
	 * {@link RequiresPermissions}
	 */
	@Pointcut(value = "@annotation(com.example.common.annotation.RequiresPermissions)")
	public void cutPermission() {

	}

	/**
	 * 环绕切面
	 *
	 * @param point
	 * @return
	 * @throws Throwable
	 */
	@Around("cutPermission()")
	public Object doPermission(ProceedingJoinPoint point) throws Throwable {
		MethodSignature ms = (MethodSignature) point.getSignature();
		Method method = ms.getMethod();
		RequiresPermissions permission = method.getAnnotation(RequiresPermissions.class);
		String[] permissions = permission.value();

		boolean hasPermission;
		if (permissions.length != 0) {
			// 校验权限
			hasPermission = check(permissions);
		} else {
			// 无参可做校验是否可以访问当前 uri
			hasPermission = checkUri();
		}

		if (hasPermission) {
			return point.proceed();
		} else {
			throw new PermissionException();
		}
	}

	/**
	 * 检查当前用户是否拥有权限
	 *
	 * @param permissions 权限集合
	 * @return 是否有权限
	 */
	public abstract boolean check(String[] permissions);

	/**
	 * 检查当前用户是否可以访问当前 uri
	 *
	 * @return 是否有权限
	 */
	public abstract boolean checkUri();

}
