package com.example.common.exception;

/**
 * 基础异常类
 *
 * @author Leo
 * @date 2020.02.17
 */
public class BaseException extends RuntimeException {

	private static final long serialVersionUID = -1385001874160823997L;

	private Integer code;

    private String message;

    public BaseException() {
        super();
        this.code = 500;
    }

    public BaseException(Integer code) {
        super();
        this.code = code;
    }

    public BaseException(String msg) {
        super();
        this.code = 500;
        this.message = msg;
    }

    public BaseException(Integer code, String msg) {
        super();
        this.code = code;
        this.message = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
