package com.sky.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@ApiModel(description = "订单提交数据传输对象")
public class OrdersSubmitDTO implements Serializable {

	@ApiModelProperty(value = "地址簿ID", example = "2001")
	private Long addressBookId;

	@ApiModelProperty(value = "付款方式 1微信，2支付宝", example = "1", allowableValues = "1, 2")
	private int payMethod;

	@ApiModelProperty(value = "备注", example = "请尽快送达")
	private String remark;

	@ApiModelProperty(value = "预计送达时间", example = "2023-01-01T12:00:00")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime estimatedDeliveryTime;

	@ApiModelProperty(value = "配送状态 1立即送出，0选择具体时间", example = "1", allowableValues = "0, 1")
	private Integer deliveryStatus;

	@ApiModelProperty(value = "餐具数量", example = "2")
	private Integer tablewareNumber;

	@ApiModelProperty(value = "餐具数量状态 1按餐量提供，0选择具体数量", example = "1", allowableValues = "0, 1")
	private Integer tablewareStatus;

	@ApiModelProperty(value = "打包费", example = "5")
	private Integer packAmount;

	@ApiModelProperty(value = "总金额", example = "100.00")
	private BigDecimal amount;
}