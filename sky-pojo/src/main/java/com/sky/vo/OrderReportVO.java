package com.sky.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "订单报告视图对象")
public class OrderReportVO implements Serializable {

	@ApiModelProperty(value = "日期列表，以逗号分隔", example = "2022-10-01,2022-10-02,2022-10-03")
	private String dateList;

	@ApiModelProperty(value = "每日订单数，以逗号分隔", example = "260,210,215")
	private String orderCountList;

	@ApiModelProperty(value = "每日有效订单数，以逗号分隔", example = "20,21,10")
	private String validOrderCountList;

	@ApiModelProperty(value = "订单总数", example = "685")
	private Integer totalOrderCount;

	@ApiModelProperty(value = "有效订单数", example = "51")
	private Integer validOrderCount;

	@ApiModelProperty(value = "订单完成率", example = "0.074")
	private Double orderCompletionRate;

}