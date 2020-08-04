package com.example.gateway.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.util.ArrayList;
import java.util.List;

/**
 * 配置系统终端访问标识
 *
 * @author Leo
 * @date 2020.02.28
 */
@Getter
@Setter
@RefreshScope
@ConfigurationProperties(prefix = "terminal")
public class TerminalConfigProps {

	private List<TerminalProps> configs = new ArrayList<>();

}
