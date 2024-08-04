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
@ApiModel(description = "销量前十报告视图对象")
public class SalesTop10ReportVO implements Serializable {

	@ApiModelProperty(value = "商品名称列表，以逗号分隔", example = "鱼香肉丝,宫保鸡丁,水煮鱼")
	private String nameList;

	@ApiModelProperty(value = "销量列表，以逗号分隔", example = "260,215,200")
	private String numberList;

}