package com.sky.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SetmealPageQueryDTO implements Serializable {

	private int page;

	private int pageSize;

	private String name;

	@ApiModelProperty(value = "分类ID", example = "2001")
	private Integer categoryId;

	@ApiModelProperty(value = "状态 0表示禁用 1表示启用", example = "1", allowableValues = "0, 1")
	private Integer status;
}