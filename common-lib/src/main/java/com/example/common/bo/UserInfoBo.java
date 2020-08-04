package com.example.common.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户信息表
 *
 * @author Leo
 * @since 2020-02-24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(description = "用户信息Bo")
public class UserInfoBo implements Serializable {

	private static final long serialVersionUID = -7541358045212078334L;

	@ApiModelProperty(value = "user_info表主键")
	private Long userId;

	@ApiModelProperty(value = "组织ID")
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

	@ApiModelProperty(value = "身份信息ID（user_auth.id）")
	private Long authId;

	@ApiModelProperty(value = "登录类型：1->用户名；2->手机号码；3->邮箱；4->身份证号码；5->社会信用代码；6->第三方平台")
	private Integer identityType;

	@ApiModelProperty(value = "登录次数（登录次数超过5次时，锁定帐户）")
	private Integer loginCount;

	@ApiModelProperty(value = "登录态令牌")
	private String token;

	@ApiModelProperty(value = "过期时间戳，单位(毫秒)")
	private String expireTime;

	@ApiModelProperty(value = "最后登录IP")
	private String loginIp;

	@ApiModelProperty(value = "租户ID")
	private Integer tenantId;

	@ApiModelProperty(value = "登陆标识")
	private String identifier;
}
