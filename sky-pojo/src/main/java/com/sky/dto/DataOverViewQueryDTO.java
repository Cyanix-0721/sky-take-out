package com.sky.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "数据概览查询数据传输对象")
public class DataOverViewQueryDTO implements Serializable {

	@ApiModelProperty(value = "开始时间", example = "2023-01-01T00:00:00")
	private LocalDateTime begin;

	@ApiModelProperty(value = "结束时间", example = "2023-01-01T23:59:59")
	private LocalDateTime end;

}