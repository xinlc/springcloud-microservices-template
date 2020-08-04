package com.example.gateway.utils;

import com.example.gateway.config.TerminalConfigProps;
import com.example.gateway.config.TerminalProps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.AntPathMatcher;

/**
 * @author Leo
 * @date 2020.02.25
 */
@Slf4j
public class AccessUtil {

	/**
	 * 获取请求真实IP
	 * 不使用request.getRemoteAddr(),避免使用代理软件方式障眼真实IP
	 * 如果通过多级反向代理，X-Forwarded-For的值会是多个，X-Forwarded-For中第一个非unknown为有效真实IP
	 *
	 * @param request
	 * @return
	 */
	public static String getIpAddress(ServerHttpRequest request) {
		String ip = request.getHeaders().getFirst("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeaders().getFirst("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeaders().getFirst("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeaders().getFirst("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeaders().getFirst("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddress().getAddress().getHostAddress();
		}
		return ip;
	}

	/**
	 * 获取请求客户的主机名
	 *
	 * @param request
	 * @return
	 */
	public static String getHost(ServerHttpRequest request) {
		return request.getRemoteAddress().getHostName();
	}

	/**
	 * 获取请求域名
	 *
	 * @param request
	 * @return http://localhost:8080/
	 */
	public static String getDomain(ServerHttpRequest request) {
		return request.getURI().getScheme() + "://" + request.getURI().getAuthority() + "/";
	}

	/**
	 * 获取请求的系统终端配置信息
	 */
	public static TerminalProps getTerminalConfig(ServerHttpRequest request, TerminalConfigProps terminalConfigProps) {
		TerminalProps terminalProps = new TerminalProps();
		String uri = request.getURI().getPath();
		AntPathMatcher antPathMatcher = new AntPathMatcher();
		antPathMatcher.setCachePatterns(true);
		antPathMatcher.setCaseSensitive(true);
		antPathMatcher.setTrimTokens(true);
		antPathMatcher.setPathSeparator("/");
		terminalConfigProps.getConfigs()
				.stream()
				.anyMatch(t -> {
					boolean isMatch = antPathMatcher.match(t.getAccessFlag(), uri);
					if (isMatch) {
						terminalProps.setAccessFlag(t.getAccessFlag());
						terminalProps.setName(t.getName());
						terminalProps.setSystemId(t.getSystemId());
						terminalProps.setCookieTokenKey(t.getCookieTokenKey());
					}
					return isMatch;
				});
		return terminalProps;
	}
}


