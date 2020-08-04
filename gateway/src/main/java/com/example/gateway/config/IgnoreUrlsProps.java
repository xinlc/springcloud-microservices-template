package com.example.gateway.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.util.ArrayList;
import java.util.List;

/**
 * 配置不需要保护的资源路径，从 application.yml 中读取
 *
 * @author Leo
 * @date 2020.02.17
 */
@Getter
@Setter
@RefreshScope
@ConfigurationProperties(prefix = "secure.ignored")
public class IgnoreUrlsProps {

	private List<String> urls = new ArrayList<>();

}
