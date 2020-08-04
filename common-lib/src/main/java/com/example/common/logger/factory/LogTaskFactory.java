package com.example.common.logger.factory;

import com.example.common.bo.OperationLogBo;
import com.example.common.emum.LogTypeEnum;
import com.example.common.logger.BaseOperationLogService;
import com.example.common.utils.SpringUtil;
import com.example.common.utils.ToolUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.TimerTask;

/**
 * 日志异步任务工厂
 *
 * @author Leo
 * @date 2020.04.12
 */
public class LogTaskFactory {

	private static final Logger logger = LoggerFactory.getLogger("sys-user");

//	/**
//	 * 登录日志
//	 *
//	 * @param username
//	 * @param msg
//	 * @param ip
//	 * @return
//	 */
//	public static TimerTask loginLog(final String username, final String msg, final String ip) {
//		return new TimerTask() {
//			@Override
//			public void run() {
//				LoginLog loginLog = LogFactory.createLoginLog(
//						LogTypeEnum.LOGIN_FAIL, null, "账号:" + username + "," + msg, ip);
//				try {
//					SpringUtil.getBean(BaseLoginLogService.class).insertLog(loginLog);
//				} catch (Exception e) {
//					logger.error("创建登录失败异常!", e);
//				}
//			}
//		};
//	}

	/**
	 * 操作日志记录
	 *
	 * @param operationLogBo 操作日志信息
	 * @return 任务task
	 */
	public static TimerTask recordOperationLog(final OperationLogBo operationLogBo) {
		return new TimerTask() {
			@Override
			public void run() {
				SpringUtil.getBean(BaseOperationLogService.class).insertLog(operationLogBo);
			}
		};
	}

	/**
	 * 异常日志
	 *
	 * @param userId
	 * @param exception
	 * @return
	 */
	public static TimerTask exceptionLog(final Long userId, final Exception exception) {
		return new TimerTask() {
			@Override
			public void run() {
				String msg = ToolUtil.getExceptionMsg(exception);
				OperationLogBo operationLogBo = LogFactory.createExceptionLog(LogTypeEnum.EXCEPTION, userId, msg);
				SpringUtil.getBean(BaseOperationLogService.class).insertLog(operationLogBo);
			}
		};
	}
}
