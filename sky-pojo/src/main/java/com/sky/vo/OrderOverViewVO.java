package com.sky.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 订单概览数据
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "订单概览数据")
public class OrderOverViewVO implements Serializable {

	@ApiModelProperty(value = "待接单数量", example = "10")
	private Integer waitingOrder;

	@ApiModelProperty(value = "待派送数量", example = "5")
	private Integer deliveredOrder;

	@ApiModelProperty(value = "已完成数量", example = "20")
	private Integer completedOrder;

	@ApiModelProperty(value = "已取消数量", example = "2")
	private Integer cancelledOrder;

	@ApiModelProperty(value = "全部订单", example = "37")
	private Integer allOrder;
}