package com.example.gateway.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.support.NameUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Swagger 聚合配置
 *
 * @author Leo
 * @date 2020.03.02
 */
@Slf4j
@Component
@Primary
@AllArgsConstructor
public class SwaggerResourceConfig implements SwaggerResourcesProvider {

//	private final RouteLocator routeLocator;
//	private final GatewayProperties gatewayProperties;
//
//	@Override
//	public List<SwaggerResource> get() {
//		List<SwaggerResource> resources = new ArrayList<>();
//		List<String> routes = new ArrayList<>();
//		routeLocator.getRoutes().subscribe(route -> routes.add(route.getId()));
//		gatewayProperties.getRoutes().stream().filter(routeDefinition -> routes.contains(routeDefinition.getId())).forEach(route -> {
//			route.getPredicates().stream()
//					.filter(predicateDefinition -> ("Path").equalsIgnoreCase(predicateDefinition.getName()))
//					.forEach(predicateDefinition -> resources.add(swaggerResource(route.getId(),
//							predicateDefinition.getArgs().get(NameUtils.GENERATED_NAME_PREFIX + "0")
//									.replace("**", "v2/api-docs"))));
//		});
//
//		return resources;
//	}

	@Autowired
	private SwaggerProps swaggerProps;

	@Override
	public List<SwaggerResource> get() {
		List<SwaggerResource> resources = new ArrayList<>();
		swaggerProps.getResources()
				.forEach(props -> {
					resources.add(swaggerResource(props.getName(), props.getLocation(), props.getVersion()));
				});
		return resources;
	}

	private SwaggerResource swaggerResource(String name, String location, String version) {
		log.info("name:{},location:{}", name, location);
		SwaggerResource swaggerResource = new SwaggerResource();
		swaggerResource.setName(name);
		swaggerResource.setLocation(location);
		swaggerResource.setSwaggerVersion(version);
		return swaggerResource;
	}
}
