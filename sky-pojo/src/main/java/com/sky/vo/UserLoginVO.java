package com.sky.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * \C端用户登录视图对象
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "View Object (VO) for user login information.")
public class UserLoginVO implements Serializable {

	/**
	 * \用户ID
	 */
	@ApiModelProperty(value = "用户ID", required = true)
	private Long id;

	/**
	 * \微信的用户唯一标识
	 */
	@ApiModelProperty(value = "微信的用户唯一标识", required = true)
	private String openid;

	/**
	 * \用户登录令牌
	 */
	@ApiModelProperty(value = "用户登录令牌", required = true)
	private String token;

}