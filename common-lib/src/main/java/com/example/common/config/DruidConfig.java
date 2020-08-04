package com.example.common.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.alibaba.druid.support.spring.stat.DruidStatInterceptor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.JdkRegexpMethodPointcut;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;

import javax.servlet.Filter;
import javax.servlet.Servlet;
import javax.sql.DataSource;

/**
 * Druid 配置
 *
 * @author Leo
 * @date 2020.05.07
 */
@Configuration
@ConditionalOnProperty("spring.datasource.druid.initial-size")
public class DruidConfig {

	/**
	 * 因为默认是使用的java.sql.Datasource的类来获取属性的，有些属性datasource没有，
	 * 将所有前缀为spring.datasource下的配置项都加载DataSource中
	 */
	@Primary
	@Bean
	@ConfigurationProperties(prefix = "spring.datasource.druid")
	public DataSource druidDataSource() {
		return DruidDataSourceBuilder.create().build();
//		return new DruidDataSource();
	}

	/**
	 * 配置监控服务器
	 * http://localhost:8080/druid/login.html
	 *
	 * @return 返回监控注册的servlet对象
	 */
	@Bean
	@ConditionalOnProperty(prefix = "monitor", name = "druid", havingValue = "true")
	public ServletRegistrationBean<Servlet> statViewServlet() {
		// druid 监控的配置处理
		ServletRegistrationBean<Servlet> servletRegistrationBean = new ServletRegistrationBean<>(new StatViewServlet(), "/druid/*");
		// 添加IP白名单
		servletRegistrationBean.addInitParameter("allow", "127.0.0.1");
		// 添加IP黑名单，当白名单和黑名单重复时，黑名单优先级更高
//		servletRegistrationBean.addInitParameter("deny", "127.0.0.1");
		// 添加控制台管理用户
		servletRegistrationBean.addInitParameter("loginUsername", "admin");
		servletRegistrationBean.addInitParameter("loginPassword", "admin");
		// 是否能够重置数据
		servletRegistrationBean.addInitParameter("resetEnable", "true");
		return servletRegistrationBean;
	}

	/**
	 * 配置web监控过滤器
	 *
	 * @return 返回过滤器配置对象
	 */
	@Bean
	@ConditionalOnProperty(prefix = "monitor", name = "druid", havingValue = "true")
	public FilterRegistrationBean<Filter> webStatFilter() {
		FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>(new WebStatFilter());
//		filterRegistrationBean.setFilter(new WebStatFilter());
		// 添加过滤规则
		// 所有请求进行监控处理
		filterRegistrationBean.addUrlPatterns("/*");

		// 忽略过滤格式
		filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*,");

		// 配置profileEnable能够监控单个url调用的sql列表。
		filterRegistrationBean.addInitParameter("profileEnable", "true");

		// session 统计
		filterRegistrationBean.addInitParameter("sessionStatEnable", "true");
		// 配置principalSessionName，使得druid能够知道当前的session的用户是谁
		// 把其中的xxx.user修改为你user信息保存在session中的sessionName。
//		filterRegistrationBean.addInitParameter("principalSessionName", "xxx.user");
//		 把其中的xxx.user修改为你user信息保存在cookie中的cookieName
//		filterRegistrationBean.addInitParameter("principalCookieName", "xxx.user");
		return filterRegistrationBean;
	}

	/**
	 * 配置Druid关联spring监控 - 拦截器
	 */
	@Bean
	@ConditionalOnProperty(prefix = "monitor", name = "druid", havingValue = "true")
	public DruidStatInterceptor druidStatInterceptor() {
		DruidStatInterceptor dsInterceptor = new DruidStatInterceptor();
		return dsInterceptor;
	}

	/**
	 * 配置Druid关联spring监控 - AOP 正则表达式配置切点
	 */
	@Bean
	@Scope("prototype")
	@ConditionalOnProperty(prefix = "monitor", name = "druid", havingValue = "true")
	public JdkRegexpMethodPointcut druidStatPointcut() {
		JdkRegexpMethodPointcut pointcut = new JdkRegexpMethodPointcut();
		pointcut.setPatterns("com.example.*.service.*", "com.example.dao.*");
		return pointcut;
	}

	/**
	 * 配置Druid关联spring监控 - AOP 通知
	 *
	 * @param druidStatInterceptor
	 * @param druidStatPointcut
	 */
	@Bean
	@ConditionalOnProperty(prefix = "monitor", name = "druid", havingValue = "true")
	public DefaultPointcutAdvisor druidStatAdvisor(DruidStatInterceptor druidStatInterceptor, JdkRegexpMethodPointcut druidStatPointcut) {
		DefaultPointcutAdvisor defaultPointAdvisor = new DefaultPointcutAdvisor();
		defaultPointAdvisor.setPointcut(druidStatPointcut);
		defaultPointAdvisor.setAdvice(druidStatInterceptor);
		return defaultPointAdvisor;
	}

}
