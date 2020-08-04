package com.example.gateway.exception;

import com.example.common.api.ApiResult;
import com.example.common.api.ApiResultType;
import com.example.common.exception.BusinessException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.netty.channel.ConnectTimeoutException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * 网关异常通知
 *
 * @author Leo
 * @date 2020.02.17
 */
@Slf4j
@Component
public class GateWayExceptionHandlerAdvice {

	@ExceptionHandler(value = {ResponseStatusException.class})
	@ResponseStatus(HttpStatus.OK)
	public ApiResult<?> handle(ResponseStatusException ex) {
		log.error("response status exception:{}", ex.getMessage());
		return ApiResult.FAIL(ApiResultType.GATEWAY_ERROR.getCode());
	}

	@ExceptionHandler(value = {ConnectTimeoutException.class})
	@ResponseStatus(HttpStatus.OK)
	public ApiResult<?> handle(ConnectTimeoutException ex) {
		log.error("connect timeout exception:{}", ex.getMessage());
		return ApiResult.FAIL(ApiResultType.GATEWAY_CONNECT_TIME_OUT.getCode());
	}

	@ExceptionHandler(value = {NotFoundException.class})
	@ResponseStatus(HttpStatus.OK)
	public ApiResult<?> handle(NotFoundException ex) {
		log.error("not found exception:{}", ex.getMessage());
		return ApiResult.FAIL(ApiResultType.GATEWAY_NOT_FOUND_SERVICE.getCode());
	}

	@ExceptionHandler(value = {ExpiredJwtException.class})
	@ResponseStatus(HttpStatus.OK)
	public ApiResult<?> handle(ExpiredJwtException ex) {
		log.error("ExpiredJwtException:{}", ex.getMessage());
		return ApiResult.FAIL(ApiResultType.INVALID_TOKEN.getCode());
	}

	@ExceptionHandler(value = {SignatureException.class})
	@ResponseStatus(HttpStatus.OK)
	public ApiResult<?> handle(SignatureException ex) {
		log.error("SignatureException:{}", ex.getMessage());
		return ApiResult.FAIL(ApiResultType.INVALID_TOKEN.getCode());
	}

	@ExceptionHandler(value = {MalformedJwtException.class})
	@ResponseStatus(HttpStatus.OK)
	public ApiResult<?> handle(MalformedJwtException ex) {
		log.error("MalformedJwtException:{}", ex.getMessage());
		return ApiResult.FAIL(ApiResultType.INVALID_TOKEN.getCode());
	}

	@ExceptionHandler(value = {BusinessException.class})
	@ResponseStatus(HttpStatus.OK)
	public ApiResult<?> handle(BusinessException ex) {
		log.error("BusinessException exception:{}", ApiResultType.getMsgByCode(ex.getCode()));
		return ApiResult.FAIL(ex.getCode());
	}

	@ExceptionHandler(value = {RuntimeException.class})
	@ResponseStatus(HttpStatus.OK)
	public ApiResult<?> handle(RuntimeException ex) {
		log.error("runtime exception:{}", ex.getMessage());
		return ApiResult.FAIL(ApiResultType.SYS_ERROR.getCode());
	}

	@ExceptionHandler(value = {Exception.class})
	@ResponseStatus(HttpStatus.OK)
	public ApiResult<?> handle(Exception ex) {
		log.error("exception:{}", ex.getMessage());
		return ApiResult.FAIL(ApiResultType.SYS_ERROR.getCode());
	}

	@ExceptionHandler(value = {Throwable.class})
	@ResponseStatus(HttpStatus.OK)
	public ApiResult<?> handle(Throwable throwable) {
		throwable.printStackTrace();
		ApiResult<?> result = ApiResult.FAIL(ApiResultType.SYS_ERROR.getCode());
		if (throwable instanceof ResponseStatusException) {
			result = handle((ResponseStatusException) throwable);
		} else if (throwable instanceof ConnectTimeoutException) {
			result = handle((ConnectTimeoutException) throwable);
		} else if (throwable instanceof NotFoundException) {
			result = handle((NotFoundException) throwable);
		} else if (throwable instanceof BusinessException) {
			result = handle((BusinessException) throwable);
		} else if (throwable instanceof RuntimeException) {
			result = handle((RuntimeException) throwable);
		} else if (throwable instanceof Exception) {
			result = handle((Exception) throwable);
		}
		return result;
	}
}
