package com.sky.entity;

import com.baomidou.mybatisplus.annotation.Version;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "分类实体类")
public class Category implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "分类ID", example = "1")
	private Long id;

	@ApiModelProperty(value = "类型: 1菜品分类 2套餐分类", example = "1", allowableValues = "1, 2")
	private Integer type;

	@ApiModelProperty(value = "分类名称", example = "主菜")
	private String name;

	@ApiModelProperty(value = "顺序", example = "1")
	private Integer sort;

	@ApiModelProperty(value = "分类状态 0标识禁用 1表示启用", example = "1", allowableValues = "0, 1")
	private Integer status;

	@ApiModelProperty(value = "创建时间", example = "2023-01-01T12:00:00")
	private LocalDateTime createTime;

	@ApiModelProperty(value = "更新时间", example = "2023-01-01T12:00:00")
	private LocalDateTime updateTime;

	@ApiModelProperty(value = "创建人ID", example = "1")
	private Long createUser;

	@ApiModelProperty(value = "修改人ID", example = "1")
	private Long updateUser;
	
	@Version
	private Integer version;
}