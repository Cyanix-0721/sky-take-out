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
@ApiModel(description = "营业额报告视图对象")
public class TurnoverReportVO implements Serializable {

	@ApiModelProperty(value = "日期列表，以逗号分隔", example = "2022-10-01,2022-10-02,2022-10-03")
	private String dateList;

	@ApiModelProperty(value = "营业额列表，以逗号分隔", example = "406.0,1520.0,75.0")
	private String turnoverList;

}