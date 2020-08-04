package com.example.common.handler;

import com.baomidou.mybatisplus.extension.plugins.tenant.TenantHandler;
import com.example.common.config.MybatisPlusConfigProps;
import com.example.common.context.AuthContext;
import com.example.common.constant.ApplicationConst;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.NullValue;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * MP 多租户配置
 *
 * @author Leo
 * @date 2020.04.26
 */
@Slf4j
@Component
public class MPTenantHandler implements TenantHandler {

	@Autowired
	private MybatisPlusConfigProps mybatisPlusConfigProps;

	/**
	 * 获取租户Id
	 *
	 * @return
	 */
	@Override
	public Expression getTenantId(boolean where) {
		// 该 where 条件 3.2.0 版本开始添加的，用于分区是否为在 where 条件中使用
		// 从当前系统上下文中取出当前请求的服务商ID，通过解析器注入到SQL中。

		String tenantId = AuthContext.getTenantId();
		if (StringUtils.isBlank(tenantId)) {
			return new NullValue();
		}
		return new LongValue(tenantId);
	}

	/**
	 * 租户字段名
	 *
	 * @return
	 */
	@Override
	public String getTenantIdColumn() {
		return ApplicationConst.SYSTEM_TENANT_ID;
	}

	/**
	 * 根据表名判断是否进行过滤
	 * 忽略掉一些表：如租户表（tenant_config）本身不需要执行这样的处理
	 *
	 * @param tableName
	 * @return
	 */
	@Override
	public boolean doTableFilter(String tableName) {
		return mybatisPlusConfigProps.getIgnoreTenantTables()
				.stream()
				.anyMatch((e) -> e.equalsIgnoreCase(tableName));
	}
}
