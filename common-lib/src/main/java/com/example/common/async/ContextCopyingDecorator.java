package com.example.common.async;

import com.example.common.context.AuthContext;
import org.springframework.core.task.TaskDecorator;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * 异步任务复制上下文装饰器
 * <p>
 * https://stackoverflow.com/questions/23732089/how-to-enable-request-scope-in-async-task-executor
 *
 * @author Leo
 * @date 2020.04.12
 */
public class ContextCopyingDecorator implements TaskDecorator {
	@Override
	public Runnable decorate(Runnable runnable) {
		// 注意：setRequestAttributes 不可行，当请求完成后，请求头中的属性值会被清空，异步任务中将无法获取
//		RequestAttributes context = RequestContextHolder.currentRequestAttributes();
		AuthContext authContext = AuthContext.getCopyOfContext();

		return () -> {
			try {
//				RequestContextHolder.setRequestAttributes(context);
				AuthContext.setContext(authContext);
				runnable.run();
			} finally {
				AuthContext.clearContext();
//				RequestContextHolder.resetRequestAttributes();
			}
		};
	}
}
