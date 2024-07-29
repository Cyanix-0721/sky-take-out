package com.sky.entity;

import com.baomidou.mybatisplus.annotation.Version;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 菜品口味
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "菜品口味实体类")
public class DishFlavor implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "口味ID", example = "1")
	private Long id;

	@ApiModelProperty(value = "菜品ID", example = "1")
	private Long dishId;

	@ApiModelProperty(value = "口味名称", example = "辣")
	private String name;

	@ApiModelProperty(value = "口味数据列表", example = "['微辣', '中辣', '重辣']")
	private String value;

	@Version
	private Integer version;
}