package com.sky.vo;

import com.sky.entity.DishFlavor;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "菜品视图对象")
public class DishVO implements Serializable {

	@ApiModelProperty(value = "菜品ID", example = "1001")
	private Long id;

	@ApiModelProperty(value = "菜品名称", example = "宫保鸡丁")
	private String name;

	@ApiModelProperty(value = "菜品分类ID", example = "2001")
	private Long categoryId;

	@ApiModelProperty(value = "菜品价格", example = "29.99")
	private BigDecimal price;

	@ApiModelProperty(value = "图片", example = "image_url")
	private String image;

	@ApiModelProperty(value = "描述信息", example = "经典川菜")
	private String description;

	@ApiModelProperty(value = "状态 0:停售 1:起售", example = "1", allowableValues = "0, 1")
	private Integer status;

	@ApiModelProperty(value = "更新时间", example = "2023-01-01T12:00:00")
	private LocalDateTime updateTime;

	@ApiModelProperty(value = "分类名称", example = "川菜")
	private String categoryName;

	@ApiModelProperty(value = "菜品关联的口味")
	private List<DishFlavor> flavors = new ArrayList<>();
}