package com.sky.dto;

import com.sky.entity.SetmealDish;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@ApiModel(description = "套餐数据传输对象")
public class SetmealDTO implements Serializable {

	@ApiModelProperty(value = "套餐ID", example = "1001")
	private Long id;

	@ApiModelProperty(value = "分类ID", example = "2001")
	private Long categoryId;

	@ApiModelProperty(value = "套餐名称", example = "豪华套餐")
	private String name;

	@ApiModelProperty(value = "套餐价格", example = "99.99")
	private BigDecimal price;

	@ApiModelProperty(value = "状态 0:停用 1:启用", example = "1", allowableValues = "0, 1")
	private Integer status;

	@ApiModelProperty(value = "描述信息", example = "包含多种美味菜品")
	private String description;

	@ApiModelProperty(value = "图片", example = "image_url")
	private String image;

	@ApiModelProperty(value = "套餐菜品关系")
	private List<SetmealDish> setmealDishes = new ArrayList<>();

}