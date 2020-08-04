package com.example.common.aspect;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.collect.Lists;
import com.example.common.annotation.Log;
import com.example.common.api.ApiResult;
import com.example.common.constant.ApplicationConst;
import com.example.common.exception.GlobalExceptionHandlerAdvice;
import com.example.common.utils.IllegalStrFilterUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Controller 打印入参出参日志
 *
 * @author Leo
 * @date 2020.04.20
 */
public class BaseAspect {

	@Autowired
	private GlobalExceptionHandlerAdvice globalExceptionHandlerAdvice;

	private static final Logger log = LoggerFactory.getLogger(BaseAspect.class);
	private static final String STRING_START = "\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n";
	private static final String STRING_END = "\n<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< 耗时:{}ms <<<<<<\n";

	public Object around(ProceedingJoinPoint joinPoint) {
		// 入参合法校验
		/*checkRequestParam(joinPoint);*/

		ApiResult apiResult = null;
		StringBuffer classAndMethod = new StringBuffer();
		String target = null;
		long start = 0L;
		Object result = null;
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		Class<?> targetClass = method.getDeclaringClass();
		try {
			Log classAnnotation = targetClass.getAnnotation(Log.class);
			Log methodAnnotation = method.getAnnotation(Log.class);
			if (classAnnotation != null) {
				if (classAnnotation.ignore()) {
					return joinPoint.proceed();
				}
				classAndMethod.append(classAnnotation.value()).append("-");
			}
			if (methodAnnotation != null) {
				if (methodAnnotation.ignore()) {
					return joinPoint.proceed();
				}
				classAndMethod.append(methodAnnotation.value());
			}
			target = targetClass.getName() + "#" + method.getName();
			Class<?>[] paramTypeClass = method.getParameterTypes();
			String params = null;
			for (Class<?> paramType : paramTypeClass) {
				if (paramType.getName().contains("MultipartFile")) {
					params = "org.springframework.web.multipart.MultipartFile";
				} else {
					Object[] args = Optional.ofNullable(joinPoint.getArgs())
							.orElse(new Object[0]);
					List<Object> logArgs = Arrays.asList(args).stream()
							.filter(arg -> (!(arg instanceof HttpServletRequest) && !(arg instanceof HttpServletResponse)))
							.collect(Collectors.toList());
					params = JSONObject.toJSONStringWithDateFormat(logArgs, ApplicationConst.DATE_FORMAT, SerializerFeature.WriteMapNullValue);
				}
				break;
			}
			log.info(STRING_START + "{} 开始调用--> {} 入参:{}", classAndMethod.toString(), target, params);
			start = System.currentTimeMillis();
			result = joinPoint.proceed();
		} catch (Throwable throwable) {
			apiResult = handlerException(joinPoint, throwable);
		} finally {
			long timeConsuming = System.currentTimeMillis() - start;
			log.info("\n{} 调用结束<-- {} 出参:{}" + STRING_END, classAndMethod.toString(), target, JSONObject.toJSONStringWithDateFormat(null != apiResult ? apiResult : result, ApplicationConst.DATE_FORMAT, SerializerFeature.WriteMapNullValue), timeConsuming);
			if (null != apiResult) {
				return apiResult;
			} else {
				return result;
			}
		}
	}

	private ApiResult handlerException(ProceedingJoinPoint pjp, Throwable e) {
		return globalExceptionHandlerAdvice.handlerException(e);

//		if (e.getClass().isAssignableFrom(BusinessException.class)) {
//			BusinessException projectException = (BusinessException) e;
//			apiResult = ApiResult.FAIL(projectException.getCode());
//		} else if (e instanceof BusinessException) {
//			BusinessException projectException = (BusinessException) e;
//			apiResult = ApiResult.FAIL(projectException.getCode());
//		} else if (e instanceof ConstraintViolationException) {
//			apiResult = ApiResult.FAIL(900000);
//			Map<String, String> map = new HashMap<>(16);
//			Set<ConstraintViolation<?>> constraintViolations = ((ConstraintViolationException) e).getConstraintViolations();
//			Iterator<ConstraintViolation<?>> iterator = constraintViolations.iterator();
//			while (iterator.hasNext()) {
//				ConstraintViolation<?> cvl = iterator.next();
//				map.put(cvl.getPropertyPath().toString(), cvl.getMessageTemplate());
//			}
//			apiResult.initFailInfo(map);
//		} else {
//			log.error("RuntimeException \n 方法：{} \n 参数：{} \n 异常：{} " + pjp.getSignature(), pjp.getArgs(), e);
//			apiResult = ApiResult.FAIL(100001);
//		}
//		return apiResult;
	}

	private void checkRequestParam(ProceedingJoinPoint pjp) {
		String str = String.valueOf(pjp.getArgs());
		if (!IllegalStrFilterUtil.sqlStrFilter(str)) {
			log.info("访问接口：" + pjp.getSignature() + "，输入参数存在SQL注入风险！参数为：" + Lists.newArrayList(pjp.getArgs()).toString());
		}
		if (!IllegalStrFilterUtil.isIllegalStr(str)) {
			log.info("访问接口：" + pjp.getSignature() + ",输入参数含有非法字符!，参数为：" + Lists.newArrayList(pjp.getArgs()).toString());
		}
	}

	private ResponseEntity<byte[]> checkResponseEntity(ResponseEntity<byte[]> a) {
		return a;
	}

}
