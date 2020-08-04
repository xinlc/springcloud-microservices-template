package com.example.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * crm系统用户信息
 * @author Leo
 * @since 2020-02-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ums_crm_info")
@ApiModel(value="UmsCrmInfo对象", description="crm系统用户信息")
public class UmsCrmInfo extends Model<UmsCrmInfo> {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "地区ID")
    private Long areaId;

    @ApiModelProperty(value = "公司名称")
    private String corporateName;

    @ApiModelProperty(value = "社会信用代码")
    private String unifiedCode;

    @ApiModelProperty(value = "企业类型：1->有限责任公司，2->股份有限责任公司，3->个人独资企业，4->合伙企业")
    private Integer contactsType;

    @ApiModelProperty(value = "法人姓名")
    private String legalPerson;

    @ApiModelProperty(value = "法人身份证号码")
    private String legalPersonCard;

    @ApiModelProperty(value = "成立日期")
    private LocalDate establishDate;

    @ApiModelProperty(value = "营业期限（无限期为NULL）")
    private LocalDate termDate;

    @ApiModelProperty(value = "详细地址")
    private String address;

    @ApiModelProperty(value = "营业执照存储名称")
    private String imgName;

    @ApiModelProperty(value = "联系人姓名")
    private String contactsName;

    @ApiModelProperty(value = "联系手机号码")
    private String contactsMobile;

    @ApiModelProperty(value = "联系邮箱")
    private String contactsEmail;

    @ApiModelProperty(value = "审核状态：1->审核申请中；2->未审核；3->审核禁用；4->审核驳回；5->审核通过；")
    private Integer auditStatus;

    @ApiModelProperty(value = "逻辑删除状态：0->有效，NULL->删除")
    private Integer deleteFlag;

    @ApiModelProperty(value = "租户ID")
    private Integer tenantId;

    @ApiModelProperty(value = "创建时间（系统时间）")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "创建人ID")
    private Long createUserId;

    @ApiModelProperty(value = "修改时间（系统时间）")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "修改人ID")
    private Long updateUserId;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
