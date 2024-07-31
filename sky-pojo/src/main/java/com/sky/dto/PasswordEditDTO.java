package com.sky.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "密码编辑数据传输对象")
public class PasswordEditDTO implements Serializable {

    @ApiModelProperty(value = "员工ID", example = "1001")
    private Long empId;

    @ApiModelProperty(value = "旧密码", example = "oldPassword123")
    private String oldPassword;

    @ApiModelProperty(value = "新密码", example = "newPassword123")
    private String newPassword;

}