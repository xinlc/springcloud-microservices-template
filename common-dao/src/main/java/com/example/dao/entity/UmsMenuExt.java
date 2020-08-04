package com.example.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * [系]菜单扩展表
 * @author Leo
 * @since 2020-05-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ums_menu_ext")
@ApiModel(value="UmsMenuExt对象", description="[系]菜单扩展表")
public class UmsMenuExt extends Model<UmsMenuExt> {

    private static final long serialVersionUID=1L;

	@ApiModelProperty(value = "主键")
	@TableId(value = "id", type = IdType.AUTO)
	private Long id;

	@ApiModelProperty(value = "父级ID")
	private Long pid;

	@ApiModelProperty(value = "业务系统ID（biz_system.id）")
	private Long systemId;

	@ApiModelProperty(value = "资源ID（biz_resource.id）")
	private Long resourceId;

	@ApiModelProperty(value = "权限标识（如：ums:user:add）")
	private String perms;

	@ApiModelProperty(value = "标题")
	private String title;

	@ApiModelProperty(value = "类型：0->目录；1->菜单；2->按钮；")
	private Integer menuType;

	@ApiModelProperty(value = "前端路由名称")
	private String menuName;

	@ApiModelProperty(value = "前端路由地址")
	private String uri;

	@ApiModelProperty(value = "前端图标")
	private String icon;

	@ApiModelProperty(value = "控制前端隐藏：0->显示；1->隐藏；")
	private Integer hidden;

	@ApiModelProperty(value = "排序值，越小越靠前")
	private Integer sortOrder;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
