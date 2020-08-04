package com.example.common.emum;

/**
 * 业务操作类型
 *
 * @author Leo
 * @date 2020.04.12
 */
public enum OpBusinessTypeEnum {
	/**
	 * 其它
	 */
	OTHER(0),

	/**
	 * 新增
	 */
	INSERT(1),

	/**
	 * 修改
	 */
	UPDATE(2),

	/**
	 * 删除
	 */
	DELETE(3),

	/**
	 * 授权
	 */
	GRANT(4),

	/**
	 * 导出
	 */
	EXPORT(5),

	/**
	 * 导入
	 */
	IMPORT(6);

	private Integer value;

	OpBusinessTypeEnum(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}

	public static OpBusinessTypeEnum valueOf(Integer value) {
		for (OpBusinessTypeEnum userState : OpBusinessTypeEnum.values()) {
			if (userState.value.equals(value)) {
				return userState;
			}
		}
		return null;
	}


}
