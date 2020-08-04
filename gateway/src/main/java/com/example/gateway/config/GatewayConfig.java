package com.example.gateway.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 网关总配置入口
 *
 * @author Leo
 * @date 2020.02.28
 */
@Configuration
@Import(value = {IgnoreUrlsProps.class, TerminalConfigProps.class, SwaggerProps.class})
public class GatewayConfig {
}
