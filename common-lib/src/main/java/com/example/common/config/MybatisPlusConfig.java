package com.example.common.config;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.core.parser.ISqlParserFilter;
import com.baomidou.mybatisplus.core.parser.SqlParserHelper;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.tenant.TenantHandler;
import com.baomidou.mybatisplus.extension.plugins.tenant.TenantSqlParser;
import com.example.common.handler.MPBlockAttackSqlParserHandler;
import com.example.common.handler.MPMetaHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.ArrayList;
import java.util.List;

/**
 * Mybatis Plus 配置
 *
 * @author Leo
 * @date 2020.04.26
 */
@Slf4j
@Configuration
@ConditionalOnClass(MybatisPlusAutoConfiguration.class)
@Import(value = {MybatisPlusConfigProps.class})
public class MybatisPlusConfig {

	@Autowired
	private TenantHandler myTenantHandler;

	@Autowired
	private ISqlParserFilter mySqlParserFilter;

	@Autowired
	private MPBlockAttackSqlParserHandler blockAttackSqlParserHandler;

	@Autowired
	private MybatisPlusConfigProps mybatisPlusConfigProps;

	/**
	 * 配置 sql 解析器链，依赖 MP 分页插件
	 */
	@Bean
	public PaginationInterceptor paginationInterceptor() {
		PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
		List<ISqlParser> sqlParserList = new ArrayList<>();

		// 多租户解析
		TenantSqlParser tenantSqlParser = new TenantSqlParser();
		tenantSqlParser.setTenantHandler(myTenantHandler);
		sqlParserList.add(tenantSqlParser);

		// 攻击 SQL 阻断解析器、加入解析链
		sqlParserList.add(blockAttackSqlParserHandler);
		paginationInterceptor.setSqlParserList(sqlParserList);

		// sql 解析过滤器
		paginationInterceptor.setSqlParserFilter(mySqlParserFilter);
		return paginationInterceptor;
	}

	/**
	 * 自动填充功能
	 */
	@Bean
	public GlobalConfig globalConfig() {
		GlobalConfig globalConfig = new GlobalConfig();
		globalConfig.setMetaObjectHandler(new MPMetaHandler());
		return globalConfig;
	}

	/**
	 * 自定义拦截器，注入SQL[暂不好使，update_time使用自动补充功能]
	 */
    /*@Bean
    public SqlInterceptor sqlInterceptor() {
        return new SqlInterceptor();
    }*/

    /*@Bean
    public SqlExplainInterceptor sqlExplainInterceptor(){
        SqlExplainInterceptor sqlExplainInterceptor = new SqlExplainInterceptor();
        List<ISqlParser> sqlParserList = new ArrayList<>();
        sqlParserList.add(new BlockAttackSqlParser());
        sqlExplainInterceptor.setSqlParserList(sqlParserList);
        return sqlExplainInterceptor;
    }*/

	/**
	 * sql注入器  逻辑删除插件
	 */
//    @Bean
//    public ISqlInjector iSqlInjector(){
//        return new LogicSqlInjector();
//    }

	/**
	 * 乐观锁插件
	 */
	@Bean
	public OptimisticLockerInterceptor optimisticLockerInterceptor() {
		return new OptimisticLockerInterceptor();
	}

	/**
	 * sql 解析过滤器
	 * <p>
	 * 建议用 @SqlParser(filter=true)
	 */
	@Bean
	public ISqlParserFilter iSqlParserFilter() {
		return metaObject -> {
			MappedStatement ms = SqlParserHelper.getMappedStatement(metaObject);

			// 过滤指定SQL 不走解析器
			return mybatisPlusConfigProps.getIgnoreSqlParserMappedStatements()
					.stream()
					.anyMatch((i) -> i.equalsIgnoreCase(ms.getId()));
		};
	}
}
