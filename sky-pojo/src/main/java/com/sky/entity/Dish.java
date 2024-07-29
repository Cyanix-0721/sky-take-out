package com.sky.entity;

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
 * 菜品
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "菜品实体类")
public class Dish implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "菜品ID", example = "1")
	private Long id;

	@ApiModelProperty(value = "菜品名称", example = "宫保鸡丁")
	private String name;

	@ApiModelProperty(value = "菜品分类ID", example = "1")
	private Long categoryId;

	@ApiModelProperty(value = "菜品价格", example = "29.99")
	private BigDecimal price;

	@ApiModelProperty(value = "图片", example = "image_url")
	private String image;

	@ApiModelProperty(value = "描述信息", example = "美味的宫保鸡丁")
	private String description;

	@ApiModelProperty(value = "菜品状态 0停售 1起售", example = "1", allowableValues = "0, 1")
	private Integer status;

	@ApiModelProperty(value = "创建时间", example = "2023-01-01T12:00:00")
	private LocalDateTime createTime;

	@ApiModelProperty(value = "更新时间", example = "2023-01-01T12:00:00")
	private LocalDateTime updateTime;

	@ApiModelProperty(value = "创建人ID", example = "1")
	private Long createUser;

	@ApiModelProperty(value = "修改人ID", example = "1")
	private Long updateUser;

	@Version
	private Integer version;
}