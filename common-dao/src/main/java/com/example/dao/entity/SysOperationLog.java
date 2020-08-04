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
 * 操作日志记录
 * @author Leo
 * @since 2020-04-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_operation_log")
@ApiModel(value="OperationLog对象", description="操作日志记录")
public class SysOperationLog extends Model<SysOperationLog> {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "模块标题")
    private String title;

    @ApiModelProperty(value = "业务类型：0->其它；1->新增；2->修改；3->删除；4->授权；5->导出；6->导入")
    private Integer businessType;

    @ApiModelProperty(value = "方法名称")
    private String method;

    @ApiModelProperty(value = "请求方式")
    private String requestMethod;

    @ApiModelProperty(value = "请求URL")
    private String operUrl;

    @ApiModelProperty(value = "操作类别：0->其他；1->系统；2->管理员；3->供应商；4->采购商")
    private Integer operatorType;

    @ApiModelProperty(value = "操作人员")
    private String operName;

    @ApiModelProperty(value = "操作人ID（user_info.id）")
    private Long userId;

    @ApiModelProperty(value = "主机地址")
    private String operIp;

    @ApiModelProperty(value = "请求参数")
    private String operParam;

    @ApiModelProperty(value = "返回参数")
    private String jsonResult;

    @ApiModelProperty(value = "操作状态：0->正常；1->异常")
    private Integer operStatus;

    @ApiModelProperty(value = "创建时间（系统时间）")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "错误消息")
    private String errorMsg;

    @ApiModelProperty(value = "租户ID")
    private Integer tenantId;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
