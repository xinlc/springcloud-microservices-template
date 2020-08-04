package com.example.common.logger.factory;

import com.example.common.bo.OperationLogBo;
import com.example.common.emum.LogTypeEnum;
import com.example.common.emum.OpBusinessStatus;
import com.example.common.emum.OpBusinessTypeEnum;

/**
 * 日志工厂
 *
 * @author Leo
 * @date 2020.04.12
 */
public class LogFactory {
	/**
	 * 创建登录日志
	 */
//	public static LoginLog createLoginLog(LogType logType, Long userId, String msg, String ip) {
//		LoginLog loginLog = new LoginLog();
//		loginLog.setLogName(logType.getMessage());
//		loginLog.setUserId(userId);
//		loginLog.setCreateTime(new Date());
//		loginLog.setSucceed(LogSucceed.SUCCESS.getMessage());
//		loginLog.setIpAddress(ip);
//		loginLog.setMessage(msg);
//		return loginLog;
//	}

//	/**
//	 * 创建操作日志
//	 */
//	public static OperationLogBo createOperationLog(String title, Long userId) {
//		OperationLogBo operationLog = new OperationLogBo();
//		operationLog.setTitle(title);
//		operationLog.setBusinessType();
//		operationLog.setBusinessTypes();
//		operationLog.setMethod();
//		operationLog.setRequestMethod();
//		operationLog.setOperatorType();
//		operationLog.setOperName();
//		operationLog.setUserId(userId);
//		operationLog.setOperUrl();
//		operationLog.setOperIp();
//		operationLog.setOperParam();
//		operationLog.setJsonResult();
//		operationLog.setStatus();
//		operationLog.setErrorMsg();
//		operationLog.setOperTime();
//		return operationLog;
//	}

	/**
	 * 创建异常日志
	 *
	 * @param logTypeEnum
	 * @param userId
	 * @param errMsg
	 * @return
	 */
	public static OperationLogBo createExceptionLog(LogTypeEnum logTypeEnum, Long userId, String errMsg) {
		OperationLogBo operationLog = new OperationLogBo();
		operationLog.setBusinessType(OpBusinessTypeEnum.OTHER.getValue());
		operationLog.setOperatorType(OpBusinessTypeEnum.OTHER.getValue());
		operationLog.setUserId(userId);
		operationLog.setStatus(OpBusinessStatus.FAIL.ordinal());
		operationLog.setErrorMsg(errMsg);
		return operationLog;
	}
}
