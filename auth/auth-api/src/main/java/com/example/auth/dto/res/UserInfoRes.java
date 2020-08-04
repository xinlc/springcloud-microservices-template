package com.example.auth.dto.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户信息
 *
 * @author Leo
 * @since 2020.08.01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(description = "用户信息")
public class UserInfoRes implements Serializable {

	private static final long serialVersionUID = -3865067963590612448L;

	@ApiModelProperty(value = "地区ID")
	private Long areaId;

	@ApiModelProperty(value = "地区")
	private String cnArea;

	@ApiModelProperty(value = "审核状态：1->审核申请中；2->未审核；3->审核禁用；4->审核驳回；5->审核通过；")
	private Integer auditStatus;

	@ApiModelProperty(value = "供应商名称")
	private String supplierName;

	@ApiModelProperty(value = "主键")
	private Long id;

	@ApiModelProperty(value = "组织ID（purchaser_info.id，supplier_info.id，audit_info.id）")
	private Long orgId;

	@ApiModelProperty(value = "组织类型：1->采购方；2->供应方；3->审核机关")
	private Integer orgType;

	@ApiModelProperty(value = "头像")
	private String avatar;

	@ApiModelProperty(value = "真实姓名")
	private String realName;

	@ApiModelProperty(value = "昵称")
	private String nickName;

	@ApiModelProperty(value = "身份证号码")
	private String idCard;

	@ApiModelProperty(value = "性别：0->男；1->女")
	private Integer sex;

	@ApiModelProperty(value = "生日")
	private LocalDateTime birthday;

	@ApiModelProperty(value = "手机")
	private String mobile;

	@ApiModelProperty(value = "邮箱")
	private String email;

	@ApiModelProperty(value = "状态：1->有效；2->锁定；3->禁用；4->删除")
	private Integer state;

	@ApiModelProperty(value = "租户ID")
	private Integer tenantId;

	@ApiModelProperty(value = "创建时间（系统时间）")
	private LocalDateTime createTime;

	@ApiModelProperty(value = "修改时间（系统时间）")
	private LocalDateTime updateTime;

}
