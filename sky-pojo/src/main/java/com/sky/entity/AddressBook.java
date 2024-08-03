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

/**
 * 地址簿
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "地址簿实体类")
public class AddressBook implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "地址簿ID", example = "1")
	private Long id;

	@TableField(fill = FieldFill.INSERT_UPDATE)
	@ApiModelProperty(value = "用户ID", example = "1")
	private Long userId;

	@ApiModelProperty(value = "收货人", example = "张三")
	private String consignee;

	@ApiModelProperty(value = "手机号", example = "13800138000")
	private String phone;

	@ApiModelProperty(value = "性别 0 女 1 男", example = "1", allowableValues = "0, 1")
	private String sex;

	@ApiModelProperty(value = "省级区划编号", example = "110000")
	private String provinceCode;

	@ApiModelProperty(value = "省级名称", example = "北京市")
	private String provinceName;

	@ApiModelProperty(value = "市级区划编号", example = "110100")
	private String cityCode;

	@ApiModelProperty(value = "市级名称", example = "北京市")
	private String cityName;

	@ApiModelProperty(value = "区级区划编号", example = "110101")
	private String districtCode;

	@ApiModelProperty(value = "区级名称", example = "东城区")
	private String districtName;

	@ApiModelProperty(value = "详细地址", example = "东华门街道")
	private String detail;

	@ApiModelProperty(value = "标签", example = "家")
	private String label;

	@ApiModelProperty(value = "是否默认 0否 1是", example = "1", allowableValues = "0, 1")
	private Integer isDefault;

	@Version
	private Integer version;
}