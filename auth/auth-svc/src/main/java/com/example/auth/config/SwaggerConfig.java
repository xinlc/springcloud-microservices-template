package com.example.auth.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * Swagger 配置
 *
 * @author Leo
 * @since 2020.08.01
 */
@Configuration
@EnableSwagger2
@EnableKnife4j
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {

	@Bean(value = "authApi")
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(groupApiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.example.auth.controller"))
				.paths(PathSelectors.any())
				.build()
				.securitySchemes(securitySchemes())
				.securityContexts(securityContexts());
	}

	private ApiInfo groupApiInfo() {
		return new ApiInfoBuilder()
				.title("auth 服务")
				.description("<div style='font-size:14px;color:red;'>授权，认证服务</div>")
				.contact("Leo")
				.version("1.0")
				.build();
	}

	private List<SecurityScheme> securitySchemes() {
		List<SecurityScheme> apiKeyList = new ArrayList<>();
//		apiKeyList.add(new BasicAuth("basicAuth"));
		apiKeyList.add(new ApiKey("X-Authorization", "X-Authorization", "header"));
		return apiKeyList;
	}

	private List<SecurityContext> securityContexts() {
		List<SecurityContext> securityContexts = new ArrayList<>();
		securityContexts.add(
				SecurityContext.builder()
						.securityReferences(defaultAuth())
						.forPaths(PathSelectors.regex("/.*"))
						.build());
		return securityContexts;
	}

	private List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		List<SecurityReference> securityReferences = new ArrayList<>();
//		securityReferences.add(new SecurityReference("basicAuth", authorizationScopes));
		securityReferences.add(new SecurityReference("X-Authorization", authorizationScopes));
		return securityReferences;
	}
}
