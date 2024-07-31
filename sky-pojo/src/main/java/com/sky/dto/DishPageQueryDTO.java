package com.sky.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "菜品分页查询数据传输对象")
public class DishPageQueryDTO implements Serializable {

	@ApiModelProperty(value = "页码", example = "1")
	private int page;

	@ApiModelProperty(value = "每页记录数", example = "10")
	private int pageSize;

	@ApiModelProperty(value = "菜品名称", example = "宫保鸡丁")
	private String name;

	@ApiModelProperty(value = "分类ID", example = "10")
	private Integer categoryId;

	@ApiModelProperty(value = "状态 0表示禁用 1表示启用", example = "1", allowableValues = "0, 1")
	private Integer status;

}