package com.sky.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "订单支付数据传输对象")
public class OrderPaymentDTO implements Serializable {

	@ApiModelProperty(value = "订单号", example = "202301010001")
	private String orderNumber;

	@ApiModelProperty(value = "付款方式 1微信，2支付宝", example = "1", allowableValues = "1, 2")
	private Integer payMethod;

}