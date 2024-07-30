package com.sky.dto;

import com.sky.entity.DishFlavor;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@ApiModel(description = "菜品数据传输对象")
public class DishDTO implements Serializable {

	@ApiModelProperty(value = "菜品ID", example = "1")
	private Long id;

	@ApiModelProperty(value = "菜品名称", example = "宫保鸡丁")
	private String name;

	@ApiModelProperty(value = "菜品分类ID", example = "10")
	private Long categoryId;

	@ApiModelProperty(value = "菜品价格", example = "25.50")
	private BigDecimal price;

	@ApiModelProperty(value = "图片URL", example = "http://example.com/image.jpg")
	private String image;

	@ApiModelProperty(value = "描述信息", example = "美味的宫保鸡丁")
	private String description;

	@ApiModelProperty(value = "菜品状态，0 停售 1 起售", example = "1")
	private Integer status;

	@ApiModelProperty(value = "菜品口味列表")
	private List<DishFlavor> flavors = new ArrayList<>();

}