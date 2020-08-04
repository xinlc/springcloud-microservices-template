package com.example.auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 总配置入口
 *
 * @author Leo
 * @since 2020.08.01
 */
@Configuration
@Import(value = {TerminalConfigProps.class})
public class AppConfig {
}
