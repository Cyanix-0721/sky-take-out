package com.sky.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.Version;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 购物车
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "购物车实体类")
public class ShoppingCart implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "购物车ID", example = "1")
	private Long id;

	@ApiModelProperty(value = "名称", example = "宫保鸡丁")
	private String name;

	@TableField(fill = FieldFill.INSERT_UPDATE)
	@ApiModelProperty(value = "用户ID", example = "1")
	private Long userId;

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

	@TableField(fill = FieldFill.INSERT)
	@ApiModelProperty(value = "创建时间", example = "2023-01-01T12:00:00")
	private LocalDateTime createTime;

	@Version
	private Integer version;
}