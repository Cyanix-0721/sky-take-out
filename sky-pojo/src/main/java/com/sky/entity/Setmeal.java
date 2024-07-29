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
 * 套餐
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "套餐实体类")
public class Setmeal implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "套餐ID", example = "1")
	private Long id;

	@ApiModelProperty(value = "分类ID", example = "1")
	private Long categoryId;

	@ApiModelProperty(value = "套餐名称", example = "家庭套餐")
	private String name;

	@ApiModelProperty(value = "套餐价格", example = "99.99")
	private BigDecimal price;

	@ApiModelProperty(value = "状态 0:停用 1:启用", example = "1", allowableValues = "0, 1")
	private Integer status;

	@ApiModelProperty(value = "描述信息", example = "适合家庭聚餐的套餐")
	private String description;

	@ApiModelProperty(value = "图片", example = "image_url")
	private String image;

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