package com.sky.entity;

import com.baomidou.mybatisplus.annotation.Version;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "用户实体类")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "用户ID", example = "1")
	private Long id;

	@ApiModelProperty(value = "用户唯一标识", example = "openid_example")
	private String openid;

	@ApiModelProperty(value = "状态 0:停用 1:启用", example = "1", allowableValues = "0, 1")
	private Integer status;

	@ApiModelProperty(value = "姓名", example = "张三")
	private String name;

	@ApiModelProperty(value = "手机号", example = "13800138000")
	private String phone;

	@ApiModelProperty(value = "性别 0 女 1 男", example = "1", allowableValues = "0, 1")
	private String sex;

	@ApiModelProperty(value = "身份证号", example = "110101199003071234")
	private String idNumber;

	@ApiModelProperty(value = "头像", example = "avatar_url")
	private String avatar;

	@ApiModelProperty(value = "注册时间", example = "2023-01-01T12:00:00")
	private LocalDateTime createTime;

	@Version
	private Integer version;
}