package com.example.common.config;

import com.example.common.constant.EnvConst;
import lombok.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 环境相关的配置
 *
 * @author Leo
 * @date 2020.02.19
 */
@Data
@Builder
public class EnvConfig {

	private String name;
	private boolean debug;
	private String externalApex;
	private String internalApex;
	private String scheme;

	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	private static Map<String, EnvConfig> map;

	static {
		map = new HashMap<String, EnvConfig>();
		EnvConfig envConfig = EnvConfig.builder().name(EnvConst.ENV_DEV)
				.debug(true)
				.externalApex("zhyxy.local")
				.internalApex(EnvConst.ENV_DEV)
				.scheme("http")
				.build();
		map.put(EnvConst.ENV_DEV, envConfig);

		envConfig = EnvConfig.builder().name(EnvConst.ENV_TEST)
				.debug(true)
				.externalApex("zhyxy.local")
				.internalApex(EnvConst.ENV_DEV)
				.scheme("http")
				.build();
		map.put(EnvConst.ENV_TEST, envConfig);

		envConfig = EnvConfig.builder().name(EnvConst.ENV_UAT)
				.debug(true)
				.externalApex("zhyxy-uat.local")
				.internalApex(EnvConst.ENV_UAT)
				.scheme("http")
				.build();
		map.put(EnvConst.ENV_UAT, envConfig);

//        envConfig = EnvConfig.builder().name(EnvConstant.ENV_UAT)
//                .debug(false)
//                .externalApex("zhyxy-uat.com")
//                .internalApex(EnvConstant.ENV_UAT)
//                .scheme("https")
//                .build();
//        map.put(EnvConstant.ENV_UAT, envConfig);

		envConfig = EnvConfig.builder().name(EnvConst.ENV_PROD)
				.debug(false)
				.externalApex("zhyxy.com")
				.internalApex(EnvConst.ENV_PROD)
				.scheme("https")
				.build();
		map.put(EnvConst.ENV_PROD, envConfig);
	}

	public static EnvConfig getEnvConfg(String env) {
		EnvConfig envConfig = map.get(env);
		if (envConfig == null) {
			envConfig = map.get(EnvConst.ENV_DEV);
		}
		return envConfig;
	}
}
