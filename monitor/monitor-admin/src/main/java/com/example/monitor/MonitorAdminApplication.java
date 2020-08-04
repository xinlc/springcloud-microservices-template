package com.example.monitor;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 应用监控服务
 *
 * @author Leo
 * @date 2020.03.18
 */
@EnableAdminServer
@SpringBootApplication
public class MonitorAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(MonitorAdminApplication.class, args);
	}

}
