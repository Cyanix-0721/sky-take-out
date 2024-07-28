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
 * 订单明细
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "订单明细实体类")
public class OrderDetail implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "订单明细ID", example = "1")
	private Long id;

	@ApiModelProperty(value = "名称", example = "宫保鸡丁")
	private String name;

	@ApiModelProperty(value = "订单ID", example = "1")
	private Long orderId;

	@ApiModelProperty(value = "菜品ID", example = "1")
	private Long dishId;

	@ApiModelProperty(value = "套餐ID", example = "1")
	private Long setmealId;

	@ApiModelProperty(value = "口味", example = "微辣")
	private String dishFlavor;

	@ApiModelProperty(value = "数量", example = "2")
	private Integer number;

	@ApiModelProperty(value = "金额", example = "59.98")
	private BigDecimal amount;

	@ApiModelProperty(value = "图片", example = "image_url")
	private String image;
}