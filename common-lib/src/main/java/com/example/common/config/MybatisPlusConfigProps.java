package com.example.common.config;

import com.example.common.constant.ApplicationConst;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.util.ArrayList;
import java.util.List;

/**
 * Mybatis Plus 配置属性
 *
 * @author Leo
 * @date 2020.04.26
 */
@Getter
@Setter
@RefreshScope
@ConfigurationProperties(prefix = "mybatis-plus")
public class MybatisPlusConfigProps {

	/**
	 * 忽略多租户表名
	 */
	private List<String> ignoreTenantTables = ApplicationConst.IGNORE_TENANT_TABLES;

	/**
	 * 忽略 sql 解析过滤器
	 * <p>
	 * 如：com.baomidou.springboot.mapper.UserMapper.selectListBySQL
	 */
	private List<String> ignoreSqlParserMappedStatements = new ArrayList<>();
}
