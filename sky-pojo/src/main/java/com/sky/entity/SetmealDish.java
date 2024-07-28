package com.sky.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 套餐菜品关系
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "套餐菜品关系实体类")
public class SetmealDish implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "ID", example = "1")
	private Long id;

	@ApiModelProperty(value = "套餐ID", example = "1")
	private Long setmealId;

	@ApiModelProperty(value = "菜品ID", example = "1")
	private Long dishId;

	@ApiModelProperty(value = "菜品名称", example = "宫保鸡丁")
	private String name;

	@ApiModelProperty(value = "菜品原价", example = "29.99")
	private BigDecimal price;

	@ApiModelProperty(value = "份数", example = "2")
	private Integer copies;
}