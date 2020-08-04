package com.example.auth.config;

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
 * @since 2020.08.01
 */
@Getter
@Setter
@RefreshScope
@ConfigurationProperties(prefix = "terminal")
public class TerminalConfigProps {

	private List<TerminalProps> configs = new ArrayList<>();

}
