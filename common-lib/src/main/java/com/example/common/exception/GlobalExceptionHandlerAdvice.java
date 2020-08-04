package com.example.common.exception;

import cn.hutool.core.exceptions.ValidateException;
import com.example.common.api.ApiResult;
import com.example.common.api.ApiResultType;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.*;

/**
 * 全局异常处理
 *
 * @author Leo
 * @date 2020.04.20
 */
@Slf4j
@RestControllerAdvice
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class GlobalExceptionHandlerAdvice {

	/**
	 * 统一处理请求参数校验，@Valid @RequestBody 实体对象传参
	 */
	@ExceptionHandler({MethodArgumentNotValidException.class})
	public ApiResult<?> handler(MethodArgumentNotValidException e) {
		return getValidApiResult(e);
	}

	/**
	 * 统一处理请求参数校验，@Valid 实体对象传参
	 */
	@ExceptionHandler({BindException.class})
	public ApiResult<?> handler(BindException e) {
		return getValidApiResult(e);
	}

	/**
	 * 请求参数异常，@RequestParam(required = true) 情况
	 */
	@ExceptionHandler({MissingServletRequestParameterException.class})
	public ApiResult<?> handler(MissingServletRequestParameterException e) {
		ApiResult<Map<String, String>> apiResult = ApiResult.FAIL(ApiResultType.BUSINESS_EXCEPTION.getCode());
		Map<String, String> map = new HashMap<>();
//		map.put(e.getParameterName(), e.getMessage());
		map.put(e.getParameterName(), "不能为null");
		apiResult.initFailInfo(map);
		return apiResult;
	}

	/**
	 * 请求路径异常，@PathVariable
	 */
	@ExceptionHandler({MissingPathVariableException.class})
	public ApiResult<?> handler(MissingPathVariableException e) {
		ApiResult<Map<String, String>> apiResult = ApiResult.FAIL(ApiResultType.BUSINESS_EXCEPTION.getCode());
		Map<String, String> map = new HashMap<>();
//		map.put(e.getVariableName(), e.getMessage());
		map.put(e.getVariableName(), "不能为null");
		apiResult.initFailInfo(map);
		return apiResult;
	}

	/**
	 * 统一处理请求参数校验(普通传参)
	 */
	@ExceptionHandler({ConstraintViolationException.class})
	public ApiResult<?> handler(ConstraintViolationException e) {
		ApiResult<Map<String, String>> apiResult = ApiResult.FAIL(ApiResultType.BUSINESS_EXCEPTION.getCode());
		Map<String, String> map = new HashMap<>(16);
		Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
		Iterator<ConstraintViolation<?>> iterator = constraintViolations.iterator();
		while (iterator.hasNext()) {
			ConstraintViolation<?> cvl = iterator.next();
//			map.put(cvl.getPropertyPath().toString(), cvl.getMessageTemplate());
			String path = ((PathImpl) cvl.getPropertyPath()).getLeafNode().getName();
			map.put(path, cvl.getMessage());
		}
		apiResult.initFailInfo(map);
		return apiResult;
	}

	/**
	 * hutool 验证异常
	 */
	@ExceptionHandler({ValidateException.class})
	public ApiResult<?> handler(ValidateException e) {
		return ApiResult.FAIL(ApiResultType.BUSINESS_EXCEPTION.getCode(), e.getMessage());
	}

	/**
	 * 自定义业务异常
	 */
	@ExceptionHandler({BusinessException.class})
	public ApiResult<?> handler(BusinessException e) {
		return ApiResult.FAIL(e.getCode());
	}

	/**
	 * RPC 异常
	 */
	@ExceptionHandler({RpcException.class})
	public ApiResult<?> handler(RpcException e) {
		return ApiResult.FAIL(e.getCode(), e.getMessage());
	}

	/**
	 * 其它运行时异常
	 */
	@ExceptionHandler({RuntimeException.class})
	public ApiResult<?> handler(RuntimeException e) {
		// Leo：打印异常，方便 Debug
		e.printStackTrace();
		return ApiResult.FAIL(ApiResultType.SYS_ERROR.getCode());
	}

	@ExceptionHandler({Throwable.class})
	public ApiResult<?> handler(Throwable e) {
		e.printStackTrace();
		return ApiResult.FAIL(ApiResultType.SYS_ERROR.getCode());
	}

	/**
	 * 统一处理异常，供外部调用
	 *
	 * @param throwable
	 * @return
	 */
	public ApiResult<?> handlerException(Throwable throwable) {
		ApiResult<?> result;
		if (throwable instanceof MethodArgumentNotValidException) {
			result = handler((MethodArgumentNotValidException) throwable);
		} else if (throwable instanceof MissingServletRequestParameterException) {
			result = handler((MissingServletRequestParameterException) throwable);
		} else if (throwable instanceof MissingPathVariableException) {
			result = handler((MissingPathVariableException) throwable);
		} else if (throwable instanceof BindException) {
			result = handler((BindException) throwable);
		} else if (throwable instanceof ConstraintViolationException) {
			result = handler((ConstraintViolationException) throwable);
		} else if (throwable instanceof ValidateException) {
			result = handler((ValidateException) throwable);
		} else if (throwable instanceof BusinessException) {
			result = handler((BusinessException) throwable);
		} else if (throwable instanceof RpcException) {
			result = handler((RpcException) throwable);
		} else if (throwable instanceof RuntimeException) {
			result = handler((RuntimeException) throwable);
		} else {
			result = handler(throwable);
		}
		return result;
	}

	/**
	 * 处理 @Valid 异常
	 * 使用 @Valid 加 @RequestBody 和不加异常类型一样
	 * 详见：https://github.com/spring-projects/spring-framework/issues/14790
	 *
	 * @param e MethodArgumentNotValidException || BindException
	 * @return ApiResult
	 */
	private ApiResult<Object> getValidApiResult(Exception e) {
		List<FieldError> list;
		if (e instanceof MethodArgumentNotValidException) {
			list = ((MethodArgumentNotValidException) e).getBindingResult().getFieldErrors();
		} else {
			list = ((BindException) e).getBindingResult().getFieldErrors();
		}
		ApiResult<Object> apiResult;
		apiResult = ApiResult.FAIL(ApiResultType.BUSINESS_EXCEPTION.getCode());
		Map<String, String> map = new HashMap<>(16);
		for (FieldError fieldError : list) {
			map.put(fieldError.getField(), fieldError.getDefaultMessage());
		}
		apiResult.initFailInfo(map);
		return apiResult;
	}
}
