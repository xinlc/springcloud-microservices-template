package com.example.common.logger;

import com.example.common.bo.OperationLogBo;

/**
 * 操作日志 Base 接口
 *
 * @author Leo
 * @date 2020.04.12
 */
public interface BaseOperationLogService {
	/**
	 * 新增操作日志
	 *
	 * @param operationLogBo 操作日志对象
	 */
	public void insertLog(OperationLogBo operationLogBo);
}
