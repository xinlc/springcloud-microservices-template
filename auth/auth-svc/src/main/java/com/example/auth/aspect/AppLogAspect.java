package com.example.auth.aspect;

import com.example.common.aspect.BaseAspect;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 统一日志 AOP
 *
 * @author Leo
 * @since 2020.08.01
 */
@Aspect
@Order(100)
@Component
public class AppLogAspect extends BaseAspect {

	@Pointcut("execution(* com.example.auth.controller..*(..))\n" +
			"&& !execution(* com.example.auth.controller.TestExportController*.*(..))")
	public void pointcut() {
	}

	@Around("pointcut()")
	@Override
	public Object around(ProceedingJoinPoint joinPoint) {
		return super.around(joinPoint);
	}
}
