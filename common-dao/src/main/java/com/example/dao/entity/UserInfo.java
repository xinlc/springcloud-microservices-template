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
 * 用户信息表
 * @author Leo
 * @since 2020-01-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user_info")
@ApiModel(value="UserInfo对象", description="用户信息表")
public class UserInfo extends Model<UserInfo> {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
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

    @ApiModelProperty(value = "地区ID")
    private Long areaId;

    @ApiModelProperty(value = "职业")
    private String job;

    @ApiModelProperty(value = "个性签名")
    private String personalSignature;

    @ApiModelProperty(value = "所属部门")
    private String deptName;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
