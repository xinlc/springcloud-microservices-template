package com.example.common.context;

import com.alibaba.fastjson.JSON;
import com.example.common.bo.UserInfoBo;
import com.example.common.constant.AuthConst;
import com.example.common.utils.OkHttpUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 用于保存当前 userId 和 authz 信息的上下文容器类
 *
 * @author Leo
 * @date 2020.02.19
 */
public class AuthContext {
	private static final ThreadLocal<AuthContext> contextHolder = new ThreadLocal<>();

	/**
	 * 当前用户信息
	 */
	private UserInfoBo userInfoBo;

	/**
	 * 权限信息
	 */
	private String authz;

	/**
	 * 租户 id
	 */
	private String tenantId;

	/**
	 * 系统ID
	 */
	private String systemId;

	/**
	 * 获取上下文
	 *
	 * @return
	 */
	private static AuthContext getContext() {
		AuthContext authContext = contextHolder.get();
		if (null == authContext) {
			authContext = createEmptyContext();
			contextHolder.set(authContext);
		}
		return authContext;
	}

	/**
	 * 获取上下文副本
	 *
	 * @return
	 */
	public static AuthContext getCopyOfContext() {
		AuthContext authContext = createEmptyContext();
		authContext.authz = AuthContext.getAuthz();
		authContext.userInfoBo = AuthContext.getUserInfoBo();
		authContext.tenantId = AuthContext.getTenantId();
		authContext.systemId = AuthContext.getSystemId();
		return authContext;
	}

	/**
	 * 设置 上下文
	 *
	 * @param context
	 */
	public static void setContext(AuthContext context) {
		contextHolder.set(context);
	}

	/**
	 * 清空上下文
	 */
	public static void clearContext() {
		contextHolder.remove();
	}

	/**
	 * 创建空上下文
	 *
	 * @return
	 */
	private static AuthContext createEmptyContext() {
		return new AuthContext();
	}

	/**
	 * 从请求中获取请求头
	 *
	 * @param headerName
	 * @return
	 */
	private static String getRequestHeader(String headerName) {
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		if (requestAttributes instanceof ServletRequestAttributes) {
			HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
			return request.getHeader(headerName);
		}
		return null;
	}

	public static UserInfoBo getUserInfoBo() {
		UserInfoBo userInfoBo = AuthContext.getContext().userInfoBo;
		if (null != userInfoBo) {
			return userInfoBo;
		}

		String jsonUser = getRequestHeader(AuthConst.CURRENT_USER_HEADER);
		if (null != jsonUser) {
			return JSON.parseObject(OkHttpUtil.getValueDecode(jsonUser), UserInfoBo.class);
		}
		return null;
	}

	public static String getAuthz() {
		String auth = AuthContext.getContext().authz;
		if (StringUtils.isNotBlank(auth)) {
			return auth;
		}
		return getRequestHeader(AuthConst.AUTHORIZATION_HEADER);
	}

	public static String getTenantId() {
		String tenantId = AuthContext.getContext().tenantId;
		if (StringUtils.isNotBlank(tenantId)) {
			return tenantId;
		}
		return getRequestHeader(AuthConst.CURRENT_TENANT_ID_HEADER);
	}

	public static String getSystemId() {
		String systemId = AuthContext.getContext().systemId;
		if (StringUtils.isNotBlank(systemId)) {
			return systemId;
		}
		return getRequestHeader(AuthConst.CURRENT_SYSTEM_ID_HEADER);
	}

	public void setUserInfoBo(UserInfoBo userInfoBo) {
		AuthContext.getContext().userInfoBo = userInfoBo;
	}

	public void setAuthz(String authz) {
		AuthContext.getContext().authz = authz;
	}

	public static void setTenantId(String tenantId) {
		AuthContext.getContext().tenantId = tenantId;
	}

	public void setSystemId(String systemId) {
		AuthContext.getContext().systemId = systemId;
	}
}
