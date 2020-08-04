package com.example.common.aspect;

import com.example.common.annotation.OperationLog;
import com.example.common.context.AuthContext;
import com.example.common.bo.UserInfoBo;
import com.example.common.logger.factory.LogTaskFactory;
import com.example.common.utils.AccessUtil;
import com.example.common.utils.StringUtil;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.common.bo.OperationLogBo;
import com.example.common.emum.OpBusinessStatus;
import com.example.common.async.AsyncManager;
import com.example.common.utils.JsonUtil;
import com.example.common.utils.ServletUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * 操作日志记录 AOP
 *
 * @author Leo
 * @date 2020.04.12
 */
public abstract class BaseOperationLogAspect {

	private static final Logger log = LoggerFactory.getLogger(BaseOperationLogAspect.class);

	/**
	 * 配置织入点
	 * {@link com.example.common.annotation.OperationLog}
	 */
	public void logPointCut() {
	}

	/**
	 * 处理完请求后执行
	 *
	 * @param joinPoint 切点
	 */
	public void doAfterReturning(JoinPoint joinPoint, Object jsonResult) {
		handleLog(joinPoint, null, jsonResult);
	}

	/**
	 * 拦截异常操作
	 *
	 * @param joinPoint 切点
	 * @param e         异常
	 */
	public void doAfterThrowing(JoinPoint joinPoint, Exception e) {
		handleLog(joinPoint, e, null);
	}

	/**
	 * 处理日志
	 *
	 * @param joinPoint
	 * @param e
	 * @param jsonResult
	 */
	protected void handleLog(final JoinPoint joinPoint, final Exception e, Object jsonResult) {
		try {
			// 获得注解
			OperationLog controllerLog = getAnnotationLog(joinPoint);
			if (null == controllerLog) {
				return;
			}

			// 获取当前的用户
			UserInfoBo userInfoBo = AuthContext.getUserInfoBo();
			if (null == userInfoBo) {
				return;
			}

			// 构造日志
			OperationLogBo operationLogBo = new OperationLogBo();
			operationLogBo.setStatus(OpBusinessStatus.SUCCESS.ordinal());

			// 请求的地址
			String ip = AccessUtil.getIpAddress(ServletUtil.getRequest());
			operationLogBo.setOperIp(ip);
			operationLogBo.setJsonResult(JsonUtil.marshal(jsonResult));
			operationLogBo.setOperUrl(ServletUtil.getRequest().getRequestURI());
			operationLogBo.setOperName(userInfoBo.getRealName());
			operationLogBo.setUserId(userInfoBo.getUserId());
			operationLogBo.setTenantId(userInfoBo.getTenantId());

			if (e != null) {
				operationLogBo.setStatus(OpBusinessStatus.FAIL.ordinal());
				operationLogBo.setErrorMsg(StringUtil.substring(e.getMessage(), 0, 2000));
			}

			// 设置方法名称
			String className = joinPoint.getTarget().getClass().getName();
			String methodName = joinPoint.getSignature().getName();
			operationLogBo.setMethod(className + "." + methodName + "()");

			// 设置请求方式
			operationLogBo.setRequestMethod(ServletUtil.getRequest().getMethod());

			// 处理设置注解上的参数
			getControllerMethodDescription(controllerLog, operationLogBo);

			// 保存数据库
			AsyncManager.me().execute(LogTaskFactory.recordOperationLog(operationLogBo));
		} catch (Exception exp) {
			// 记录本地异常日志
			log.error("==前置通知异常==");
			log.error("异常信息:{}", exp.getMessage());
			exp.printStackTrace();
		}
	}

	/**
	 * 获取注解中对方法的描述信息 用于Controller层注解
	 *
	 * @param operationLog   日志
	 * @param operationLogBo 操作日志
	 * @throws Exception
	 */
	public void getControllerMethodDescription(OperationLog operationLog, OperationLogBo operationLogBo) throws Exception {
		// 设置action动作
		operationLogBo.setBusinessType(operationLog.businessType().ordinal());
		// 设置标题
		operationLogBo.setTitle(operationLog.title());
		// 设置操作人类别
		operationLogBo.setOperatorType(operationLog.operatorType().ordinal());
		// 是否需要保存request，参数和值
		if (operationLog.isSaveRequestData()) {
			// 获取参数的信息，传入到数据库中。
			setRequestValue(operationLogBo);
		}
	}

	/**
	 * 获取请求的参数，放到log中
	 *
	 * @param operationLogBo 操作日志
	 * @throws Exception 异常
	 */
	private void setRequestValue(OperationLogBo operationLogBo) throws Exception {
		Map<String, String[]> map = ServletUtil.getRequest().getParameterMap();
		String params = JsonUtil.marshal(map);
		operationLogBo.setOperParam(StringUtil.substring(params, 0, 2000));
	}

	/**
	 * 是否存在注解，如果存在就获取
	 */
	private OperationLog getAnnotationLog(JoinPoint joinPoint) throws Exception {
		Signature signature = joinPoint.getSignature();
		MethodSignature methodSignature = (MethodSignature) signature;
		Method method = methodSignature.getMethod();

		if (method != null) {
			return method.getAnnotation(OperationLog.class);
		}
		return null;
	}
}
