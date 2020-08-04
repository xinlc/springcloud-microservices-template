package com.example.common.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * MP 自动填充功能
 *
 * @author Leo
 * @date 2020.04.26
 */
@Slf4j
@Component
public class MPMetaHandler implements MetaObjectHandler {

	/**
	 * 新增数据执行
	 *
	 * @param metaObject
	 */
	@Override
	public void insertFill(MetaObject metaObject) {
		this.setFieldValByName("createTime", LocalDateTime.now(), metaObject);
		this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
	}

	/**
	 * 更新数据执行
	 *
	 * @param metaObject
	 */
	@Override
	public void updateFill(MetaObject metaObject) {
		this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
	}
}
