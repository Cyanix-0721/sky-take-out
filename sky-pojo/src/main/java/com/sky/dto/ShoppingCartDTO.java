package com.sky.dto;

import lombok.Data;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Data
@ApiModel(description = "购物车数据传输对象")
public class ShoppingCartDTO implements Serializable {

	@ApiModelProperty(value = "菜品ID", example = "1")
	private Long dishId;

	@ApiModelProperty(value = "套餐ID", example = "1")
	private Long setmealId;

	@ApiModelProperty(value = "菜品口味", example = "辣")
	private String dishFlavor;
}