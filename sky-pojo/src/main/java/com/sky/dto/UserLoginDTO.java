package com.sky.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * \C端用户登录
 */
@Data
@ApiModel(description = "Data Transfer Object (DTO) for user login information.")
public class UserLoginDTO implements Serializable {

	/**
	 * \微信生成的临时登录凭证code
	 */
	@ApiModelProperty(value = "微信生成的临时登录凭证code", required = true)
	private String code;

}