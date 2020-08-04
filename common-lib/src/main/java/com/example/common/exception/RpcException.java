package com.example.common.exception;

/**
 * RPC 异常
 *
 * @author Leo
 * @date 2020.04.29
 */
public class RpcException extends BaseException {

	private static final long serialVersionUID = -3965154809752475070L;

	public RpcException(Integer code, String msg) {
        super(code, msg);
    }
}
