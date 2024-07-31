package com.sky.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "订单确认数据传输对象")
public class OrdersConfirmDTO implements Serializable {

	@ApiModelProperty(value = "订单ID", example = "12345")
	private Long id;

	@ApiModelProperty(value = "订单状态 1待付款 2待接单 3 已接单 4 派送中 5 已完成 6 已取消 7 退款", example = "1", allowableValues = "1, 2, 3, 4, 5, 6, 7")
	private Integer status;

}