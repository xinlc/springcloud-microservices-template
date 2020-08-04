package com.example.common.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 操作日志 Bo
 *
 * @author Leo
 * @date 2020.04.12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(description = "操作日志")
public class OperationLogBo implements Serializable {

	private static final long serialVersionUID = 1291007310807287982L;

	@ApiModelProperty(value = "日志主键")
	private Long id;

	@ApiModelProperty(value = "操作模块")
	private String title;

	@ApiModelProperty(value = "业务类型：0->其它；1->新增；2->修改；3->删除；4->授权；5->导出；6->导入")
	private Integer businessType;

	@ApiModelProperty(value = "业务类型数组")
	private Integer[] businessTypes;

	@ApiModelProperty(value = "请求方法")
	private String method;

	@ApiModelProperty(value = "请求方式")
	private String requestMethod;

	@ApiModelProperty(value = "操作类别：0->其他；1->系统；2->管理员；3->供应商；4->采购商")
	private Integer operatorType;

	@ApiModelProperty(value = "操作人员")
	private String operName;

	@ApiModelProperty(value = "操作人ID")
	private Long userId;

	@ApiModelProperty(value = "请求url")
	private String operUrl;

	@ApiModelProperty(value = "操作地址")
	private String operIp;

	@ApiModelProperty(value = "请求参数")
	private String operParam;

	@ApiModelProperty(value = "返回参数")
	private String jsonResult;

	@ApiModelProperty(value = "操作状态：0->正常；1->异常")
	private Integer status;

	@ApiModelProperty(value = "错误消息")
	private String errorMsg;

	@ApiModelProperty(value = "操作时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime operTime;

	@ApiModelProperty(value = "租户ID")
	private Integer tenantId;
}
