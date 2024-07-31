package com.sky.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@ApiModel(description = "订单分页查询数据传输对象")
public class OrdersPageQueryDTO implements Serializable {

	@ApiModelProperty(value = "页码", example = "1")
	private int page;

	@ApiModelProperty(value = "每页记录数", example = "10")
	private int pageSize;

	@ApiModelProperty(value = "订单号", example = "202301010001")
	private String number;

	@ApiModelProperty(value = "手机号", example = "13800138000")
	private String phone;

	@ApiModelProperty(value = "订单状态 1待付款，2待派送，3已派送，4已完成，5已取消", example = "1", allowableValues = "1, 2, 3, 4, 5")
	private Integer status;

	@ApiModelProperty(value = "开始时间", example = "2023-01-01T12:00:00")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime beginTime;

	@ApiModelProperty(value = "结束时间", example = "2023-01-01T12:30:00")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime endTime;

	@ApiModelProperty(value = "用户ID", example = "1001")
	private Long userId;
}