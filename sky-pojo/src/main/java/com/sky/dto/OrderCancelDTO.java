package com.sky.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "订单取消数据传输对象")
public class OrderCancelDTO implements Serializable {

	@ApiModelProperty(value = "订单ID", example = "12345")
	private Long id;

	@ApiModelProperty(value = "订单取消原因", example = "客户要求")
	private String cancelReason;

}