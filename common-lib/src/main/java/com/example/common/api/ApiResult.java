package com.example.common.api;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Leo
 * @date 2020.02.19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResult<T> {

	@ApiModelProperty("请求编码")
	private Integer code;

	@ApiModelProperty("请求信息")
	private String msg;

	@ApiModelProperty("总数量条数")
	private Long count;

	@ApiModelProperty("数据")
	private T data;

	public boolean isSuccess() {
		return code == 0;
	}

	public static class Builder {
		public static <T> ApiResult<T> SUCCESS() {
			ApiResult<T> vo = new ApiResult<>();
			vo.setCode(0);
			return vo;
		}

		public static <T> ApiResult<T> FAIL() {
			ApiResult<T> vo = new ApiResult<>();
			return vo;
		}
	}

	public static <T> ApiResult<T> SUCCESS() {
		ApiResult<T> result = new ApiResult<>();
		result.setCode(ApiResultType.SUCCESS.getCode());
		result.setMsg(ApiResultType.SUCCESS.getMessage());
		return result;
	}

	public static <T> ApiResult<T> SUCCESS(int code, String msg) {
		return ApiResult.SUCCESS(code, msg, null);
	}

	public static <T> ApiResult<T> SUCCESS(int code, String msg, T data) {
		ApiResult<T> result = ApiResult.Builder.SUCCESS();
		result.setCode(code);
		result.setMsg(msg);
		result.setData(data);
		return result;
	}

	public ApiResult<T> initSuccessInfo(T data) {
		this.data = data;
		return this;
	}

	public ApiResult<T> initSuccessInfo(T data, Long count) {
		this.data = data;
		this.count = count;
		return this;
	}

	public static <T> ApiResult<T> FAIL(int code) {
		ApiResult<T> result = new ApiResult<>();
		result.setCode(code);
		result.setMsg(ApiResultType.getMsgByCode(code));
		return result;
	}

	public static <T> ApiResult<T> FAIL(int code, String msg) {
		return ApiResult.FAIL(code, msg, null);
	}

	public static <T> ApiResult<T> FAIL(int code, String msg, T data) {
		ApiResult<T> result = ApiResult.Builder.FAIL();
		result.setCode(code);
		result.setMsg(msg);
		result.setData(data);
		return result;
	}

	public ApiResult<T> initFailInfo(T data) {
		this.data = data;
		return this;
	}

	public ApiResult<T> initFailInfo(T data, Long count) {
		this.data = data;
		this.count = count;
		return this;
	}
}
