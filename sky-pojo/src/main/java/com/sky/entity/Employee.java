package com.sky.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "员工实体类，表示员工的详细信息")
public class Employee implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "员工ID")
	private Long id;

	@ApiModelProperty(value = "员工用户名")
	private String username;

	@ApiModelProperty(value = "员工姓名")
	private String name;

	@ApiModelProperty(value = "员工密码")
	private String password;

	@ApiModelProperty(value = "员工电话")
	private String phone;

	@ApiModelProperty(value = "员工性别")
	private String sex;

	@ApiModelProperty(value = "员工身份证号")
	private String idNumber;

	@ApiModelProperty(value = "员工状态，1表示正常，0表示锁定")
	private Integer status;

	//@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "记录创建时间")
	private LocalDateTime createTime;

	//@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "记录更新时间")
	private LocalDateTime updateTime;

	@ApiModelProperty(value = "记录创建人ID")
	private Long createUser;

	@ApiModelProperty(value = "记录更新人ID")
	private Long updateUser;

}