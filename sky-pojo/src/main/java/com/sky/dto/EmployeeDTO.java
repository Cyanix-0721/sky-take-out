package com.sky.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "员工数据传输对象，表示员工的基本信息")
public class EmployeeDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "员工ID")
	private Long id;

	@ApiModelProperty(value = "员工用户名")
	private String username;

	@ApiModelProperty(value = "员工姓名")
	private String name;

	@ApiModelProperty(value = "员工电话")
	private String phone;

	@ApiModelProperty(value = "员工性别")
	private String sex;

	@ApiModelProperty(value = "员工身份证号")
	private String idNumber;
}