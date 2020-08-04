package com.example.common.resolvers;

import com.example.common.annotation.CurrentUser;
import com.example.common.context.AuthContext;
import com.example.common.bo.UserInfoBo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 用于绑定 @CurrentUser 的方法参数解析器
 *
 * @author Leo
 * @date 2020.02.25
 */
@Slf4j
public class CurrentUserResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterType().isAssignableFrom(UserInfoBo.class) && parameter.hasParameterAnnotation(CurrentUser.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		return AuthContext.getUserInfoBo();
	}

}
