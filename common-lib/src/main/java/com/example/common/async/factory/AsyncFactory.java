package com.example.common.async.factory;

import com.example.common.bo.OperationLogBo;
import com.example.common.logger.BaseOperationLogService;
import com.example.common.utils.SpringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.TimerTask;

/**
 * 异步工厂，产生任务
 *
 * @author Leo
 * @date 2020.04.12
 */
public class AsyncFactory {
	private static final Logger logger = LoggerFactory.getLogger("sys-user");

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
}
