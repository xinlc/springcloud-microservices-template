package com.example.common.redis;

import cn.hutool.core.util.RandomUtil;
import com.example.common.api.ApiResultType;
import com.example.common.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 幂等 redis 生成 token
 *
 * @author Leo
 * @date 2020.04.29
 */
@Component
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class RedisTokenService {

	@Autowired
	private RedisService redisService;

	private static final String TOKEN_NAME = "token";

	// 过期时间, 300s, 五分钟
	private static final Long EXPIRE_TIME = 300L;

	private static final String TOKEN_PREFIX = "token:";

	/**
	 * 创建 token，controller 调用
	 *
	 * @return token
	 */
	public String createToken() {
		String str = RandomUtil.randomString(24);
		StringBuilder token = new StringBuilder();
		token.append(TOKEN_PREFIX).append(str);
		redisService.set(token.toString(), token.toString(), EXPIRE_TIME);
		return token.toString();
	}

	/**
	 * 校验 token
	 *
	 * @param request
	 */
	public void checkToken(HttpServletRequest request) {
		String token = request.getHeader(TOKEN_NAME);

		// header 中不存在 token
		if (StringUtils.isBlank(token)) {
			token = request.getParameter(TOKEN_NAME);

			// parameter中不存在 token
			if (StringUtils.isBlank(token)) {
				throw new BusinessException(ApiResultType.ARGUMENT_NOT_VALID.getCode());
			}
		}

		if (!redisService.exists(token)) {
			throw new BusinessException(ApiResultType.REPETITIVE_OPERATION.getCode());
		}

		redisService.remove(token);
	}
}
