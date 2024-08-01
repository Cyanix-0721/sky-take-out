package com.sky.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "菜品选项视图对象")
public class DishItemVO implements Serializable {

	@ApiModelProperty(value = "菜品名称", example = "宫保鸡丁")
	private String name;

	@ApiModelProperty(value = "份数", example = "2")
	private Integer copies;

	@ApiModelProperty(value = "菜品图片", example = "image_url")
	private String image;

	@ApiModelProperty(value = "菜品描述", example = "这是一道经典的川菜")
	private String description;
}