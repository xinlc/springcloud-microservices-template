package com.example.auth.utils;

import com.example.auth.config.TerminalProps;
import com.example.auth.config.TerminalConfigProps;

/**
 * 系统终端工具类
 *
 * @author Leo
 * @since 2020.08.01
 */
public class TerminalUtil {

	/**
	 * 获取请求的系统终端配置信息
	 */
	public static TerminalProps getTerminalConfig(String systemId, TerminalConfigProps terminalConfigProps) {
		TerminalProps terminalProps = new TerminalProps();
		terminalConfigProps.getConfigs()
				.stream()
				.anyMatch(t -> {
					boolean isMatch = t.getSystemId().equals(systemId);
					if (isMatch) {
						terminalProps.setName(t.getName());
						terminalProps.setSystemId(t.getSystemId());
						terminalProps.setSecret(t.getSecret());
						terminalProps.setPayloadSecret(t.getPayloadSecret());
						terminalProps.setTokenHead(t.getTokenHead());
						terminalProps.setExpiration(t.getExpiration());
						terminalProps.setRefreshInterval(t.getRefreshInterval());
					}
					return isMatch;
				});
		return terminalProps;
	}
}


