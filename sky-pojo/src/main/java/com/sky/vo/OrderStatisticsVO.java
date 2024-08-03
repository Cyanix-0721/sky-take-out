package com.sky.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@ApiModel(description = "订单统计信息")
@Data
@Builder
public class OrderStatisticsVO implements Serializable {

	@ApiModelProperty(value = "待接单数量")
	private Integer toBeConfirmed;

	@ApiModelProperty(value = "待派送数量")
	private Integer confirmed;

	@ApiModelProperty(value = "派送中数量")
	private Integer deliveryInProgress;
}