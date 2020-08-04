package com.example.common.api;

/**
 * @author Leo
 */
public enum ApiResultType {
	SUCCESS(0, "成功"),
	// 系统错误码
	SYS_ERROR(100001, "系统异常"),
	SYS_UPDATE(100002, "系统升级"),
	REQUEST_ILLEGAL(100003, "请求地址非法，请联系管理员"),
	PARAMETER_ILLEGAL(100004, "请求参数错误"),
	PARAMETER_SIGN_ILLEGAL(100005, "参数签名错误"),
	PARAMETER_TOKEN_ILLEGAL(100006, "token无效或已过期"),
	IP_ILLEGAL(100007, "访问IP受限（未设置ip白名单或不在白名单列表）"),
	VERSION_LOW(100008, "您的版本过低"),
	ADD_FAIL(100009, "添加失败"),

	// 网关错误码
	SYSTEM_BUSY(100010, "系统繁忙,请稍候再试"),
	GATEWAY_NOT_FOUND_SERVICE(100011, "服务未找到"),
	GATEWAY_ERROR(100012, "网关异常"),
	GATEWAY_CONNECT_TIME_OUT(100013, "网关超时"),
	ARGUMENT_NOT_VALID(100014, "请求参数校验不通过"),
	INVALID_TOKEN(100015, "无效token"),
	UPLOAD_FILE_SIZE_LIMIT(100016, "上传文件大小超过限制"),
	DUPLICATE_PRIMARY_KEY(100017, "唯一键冲突"),
	GATEWAY_UNAUTHORIZED(100018, "未授权访问"),
	GATEWAY_RESOURCE_DENIAL(100019, "资源拒绝访问，请从网关获取资源"),
	REPETITIVE_OPERATION(100020, "重复操作"),

	// Header错误码
	HEADER_BUILD_ILLEGAL(100101, "Header参数Build-CU错误"),
	HEADER_PLATFORM_ILLEGAL(100102, "Header参数Platform-CU错误"),
	HEADER_LANGUAGE_ILLEGAL(100103, "Header参数Language错误"),

	// 基础业务错误码
	MOBILE_EXIST(110000, "手机号已注册"),
	EMAIL_EXIST(110001, "邮箱已注册"),
	SMS_CODE_FAIL(110002, "短信验证码错误或过期"),
	EMAIL_CODE_FAIL(110003, "邮箱验证码错误或过期"),
	SEND_SMS_FAILED(110004, "发送短信验证码失败"),
	SEND_EMAIL_FAILED(110005, "发送邮箱验证码失败"),
	TWO_PASSWORD_ERROR(110006, "两次密码不一致"),
	MODIFY_PASSWORD_ERROR(110007, "修改密码错误"),
	USER_LOCKED(110008, "帐户已被锁定"),
	USER_FREEZE_LOGIN(110009, "账号被冻结登录"),
	USER_LOCKED_2HOURS(110010, "账户被锁定2小时"),
	USER_PASSWORD_ERROR(110011, "用户名或密码错误"),
	USER_PASSWORD_SET(110012, "请设置登录密码"),
	USER_EXIST(110013, "用户已存在"),
	OLD_PASSWORD_ERROR(110014, "旧密码输入错误"),
	LOGON_FILE(110015, "登录失效，请去登录"),
	PASSWORD_ERROR(110016, "用户名或者密码不正确"),
	SAME_PASSWORD_ERROR(110017, "新密码不能与旧密码一致"),
	FORMAT_PASSWORD_ERROR(110018, "密码格式错误"),
	USERNAME_SAME_ERROR(110019, "用户名已存在"),
	ENTERPRISE_ALREADY_EXISTS(110020, "该企业已存在"),
	RIGHT_CAUSE_UPDATE_FAILED(110021, "权限不足，企业信息修改失败"),
	STATUS_CAUSE_UPDATE_FAILED(110022, "修改失败，当前审核状态下不可修改"),
	REQUEST_ERROR(110023, "请求错误，请稍后再试"),

	// 业务错误码
	BUSINESS_EXCEPTION(900000, "校验错误"),

	// 2001、2002 订单类业务
	ORDER_NOT_FOUND(200101, "订单没找到"),
	ORDER_CANNOT_CANCEL(200102, "订单不能关闭"),
	ORDER_CANNOT_COMPLETE(200103, "订单不能完成"),
	ORDER_CANNOT_MODIFY(200104, "订单不能修改"),
	ORDER_CANNOT_EMPTY(200105, "订单不能为空"),
	ORDER_INVALID(200106, "无效订单"),
	ORDER_PURCHASER_NOT_FOUND(200107, "该采购商暂无订单"),
	ORDER_CANNOT_SETTLE(200108, "没有商品需要结算，请检查购买的商品是否已失效"),
	ORDER_RECEIVING_ADDRESS_NOT_FOUND(200109, "收货地址没有找到"),
	ORDER_SUBMIT_GOODS_CHANGED(200110, "订单中的商品发生了变更，请重新结算"),
	ORDER_SUBMIT_FREIGHT_CANNOT_EMPTY(200111, "双方协商运费不能为空"),
	INVALID_GOODS_CANNOT_ADD_SHOPPING_TROLLEY(200112, "无效商品，无法添加至购物车"),
	SHOPPING_TROLLEY_NOT_EXISTS(200113, "购物车商品不存在"),
	ORDER_SETTLEMENT_NOT_FOUND(200114, "没有要结算的订单"),
	ORDER_SUBMIT_LOCK_PROCESSING(200115, "当前订单正在处理中"),
	ORDER_SUPPLIER_NOT_FOUND(200116, "供应商未找到"),
	ORDER_PURCHASER_NOT_AUDIT(200117, "账户未通过审核，无法提交订单"),
	ORDER_SETTLE_GOODS_CHANGED(200119, "商品发生了变更，请重新结算"),
	ORDER_SETTLE_GOODS_NO_STOCK(200120, "该商品已经被抢光了哦"),
	ORDER_NO_ORDER_TO_PAYMENT(200121, "没有要支付的订单"),
	ORDER_UPDATE_FREIGHT_PRICE_ERROR(200122, "运费不能高于原运费"),
	ORDER_INVALID_OPERATION(200123, "当前订单不能进行该操作"),
	ORDER_ITEM_NOT_FOUND(200124, "订单下无有效商品"),
	ORDER_HAS_RETURNS_PENDING(200125, "您有待处理售后订单"),
	ORDER_UPDATE_ORDER_PRICE_ERROR(200126, "订单金额不能高于原金额"),
	ORDER_BELOW_ORDER_PRICE_ERROR(200127, "订单金额不能低于原金额百分之70"),
	ORDER_SETTLE_AID_CHANGED(200128, "供应商扶贫属性发生变更，请重新结算"),

	// 2012 auth 服务相关业务
	ROLE_INFO_INVALID_OPERATION(201201, "无效的操作，请检查请求参数"),
	USER_CANNOT_DELETE(201202, "用户不可删除"),
	USER_NOT_EXISTS(201203, "用户不存在"),
	USERNAME_EXISTS(201204, "用户名已存在"),
	MOBILE_EXISTS(201205, "电话号码已存在"),
	REAL_NAME_EXISTS(201206, "员工姓名已存在"),
	CORPORATE_NAME_EXISTS(201207, "审核机构名称已存在"),
	IDENTIFIER_EXISTS(201208, "账号已存在"),
	ROLE_NOT_EXISTS(201209, "角色不存在"),
	CANNOT_UPDATE_YOURSELF(201210, "登录用户不可操作本人"),
	CANNOT_UPDATE_ADMIN(201211, "管理员账号，无权操作"),
	CANNOT_DELETE_INUSE_ROLE(201212, "角色已关联员工，无法删除"),

	// 2015 物流业务
	LOGISTICS_SHIPPER_NOT_SUPPORT(201501, "暂不支持该物流公司"),
	;

	public Integer code;
	public String message;

	ApiResultType(int i) {
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	ApiResultType(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	public static String getMsgByCode(Integer code) {
		for (ApiResultType result : ApiResultType.values()) {
			if (code.equals(result.getCode())) {
				return result.getMessage();
			}
		}
		return null;
	}
}
