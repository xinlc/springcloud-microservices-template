package com.example.common.exception;

/**
 * 请求参数异常
 *
 * @author Leo
 * @date 2020.02.17
 */
public class RequestException extends BaseException {

	private static final long serialVersionUID = -5815438693443428586L;

	public RequestException() {
		super();
	}

	public RequestException(Integer code) {
		super(code);
	}

	public RequestException(String msg) {
		super(msg);
	}

	public RequestException(Integer code, String msg) {
		super(code, msg);
	}
}

