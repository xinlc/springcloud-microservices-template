package com.example.common.exception;

/**
 * 业务异常
 *
 * @author Leo
 * @date 2020.02.17
 */
public class BusinessException extends BaseException {

	private static final long serialVersionUID = 466506466576607495L;

	public BusinessException() {
        super();
    }

    public BusinessException(int code) {
        super(code);
    }

    public BusinessException(Integer code) {
        super(code);
    }

    public BusinessException(String msg) {
        super(msg);
    }

    public BusinessException(Integer code, String msg) {
        super(code, msg);
    }
}
