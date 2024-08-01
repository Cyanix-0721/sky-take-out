package com.sky.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 菜品总览
 * <p>
 * 该类用于表示菜品的总览信息，包括已启售数量和已停售数量。
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "菜品总览视图对象")
public class DishOverViewVO implements Serializable {

	/**
	 * 已启售数量
	 * <p>
	 * 表示当前已启售的菜品数量。
	 */
	@ApiModelProperty(value = "已启售数量", example = "10")
	private Integer sold;

	/**
	 * 已停售数量
	 * <p>
	 * 表示当前已停售的菜品数量。
	 */
	@ApiModelProperty(value = "已停售数量", example = "5")
	private Integer discontinued;
}