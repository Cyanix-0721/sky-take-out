package com.sky.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "分类数据传输对象")
public class CategoryDTO implements Serializable {

	@ApiModelProperty(value = "主键", example = "1")
	private Long id;

	@ApiModelProperty(value = "类型 1 菜品分类 2 套餐分类", example = "1", allowableValues = "1, 2")
	private Integer type;

	@ApiModelProperty(value = "分类名称", example = "主菜")
	private String name;

	@ApiModelProperty(value = "排序", example = "1")
	private Integer sort;
}