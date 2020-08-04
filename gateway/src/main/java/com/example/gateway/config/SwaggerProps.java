package com.example.gateway.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.util.ArrayList;
import java.util.List;

/**
 * 配置 Swagger props
 *
 * @author Leo
 * @date 2020.02.17
 */
@Getter
@Setter
@RefreshScope
@ConfigurationProperties(prefix = "swagger")
public class SwaggerProps {

	private List<SwaggerResourceProps> resources = new ArrayList<>();

}
