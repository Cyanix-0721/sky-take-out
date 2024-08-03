package com.sky.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "订单提交视图对象")
public class OrderSubmitVO implements Serializable {
	@ApiModelProperty(value = "订单ID", example = "1001")
	private Long id;

	@ApiModelProperty(value = "订单号", example = "ORD123456")
	private String orderNumber;

	@ApiModelProperty(value = "订单金额", example = "99.99")
	private BigDecimal orderAmount;

	@ApiModelProperty(value = "下单时间", example = "2023-01-01T12:00:00")
	private LocalDateTime orderTime;
}