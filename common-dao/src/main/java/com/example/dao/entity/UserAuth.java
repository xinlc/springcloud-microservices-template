package com.example.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用户身份验证表
 * @author Leo
 * @since 2020-01-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user_auth")
@ApiModel(value="UserAuth对象", description="用户身份验证表")
public class UserAuth extends Model<UserAuth> {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户ID（user_info.id）")
    private Long userId;

    @ApiModelProperty(value = "登录类型：1->用户名；2->手机号码；3->邮箱；4->身份证号码；5->社会信用代码；6->第三方平台")
    private Integer identityType;

    @ApiModelProperty(value = "登陆标识")
    private String identifier;

    @ApiModelProperty(value = "密码凭证")
    private String credential;

    @ApiModelProperty(value = "登录次数（登录次数超过5次时，锁定帐户）")
    private Integer loginCount;

    @ApiModelProperty(value = "登录态令牌")
    private String token;

    @ApiModelProperty(value = "逻辑删除状态：0->有效；NULL->删除")
    private Integer deleteFlag;

    @ApiModelProperty(value = "过期时间戳，单位(毫秒)")
    private String expireTime;

    @ApiModelProperty(value = "最后登录IP")
    private String loginIp;

    @ApiModelProperty(value = "租户ID")
    private Integer tenantId;

    @ApiModelProperty(value = "创建时间（系统时间）")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间（系统时间）")
    private LocalDateTime updateTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
