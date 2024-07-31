package com.sky.dto;

import com.sky.entity.OrderDetail;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@ApiModel(description = "订单数据传输对象")
public class OrdersDTO implements Serializable {

	@ApiModelProperty(value = "订单ID", example = "12345")
	private Long id;

	@ApiModelProperty(value = "订单号", example = "202301010001")
	private String number;

	@ApiModelProperty(value = "订单状态 1待付款，2待派送，3已派送，4已完成，5已取消", example = "1", allowableValues = "1, 2, 3, 4, 5")
	private Integer status;

	@ApiModelProperty(value = "下单用户ID", example = "1001")
	private Long userId;

	@ApiModelProperty(value = "地址ID", example = "2001")
	private Long addressBookId;

	@ApiModelProperty(value = "下单时间", example = "2023-01-01T12:00:00")
	private LocalDateTime orderTime;

	@ApiModelProperty(value = "结账时间", example = "2023-01-01T12:30:00")
	private LocalDateTime checkoutTime;

	@ApiModelProperty(value = "支付方式 1微信，2支付宝", example = "1", allowableValues = "1, 2")
	private Integer payMethod;

	@ApiModelProperty(value = "实收金额", example = "100.00")
	private BigDecimal amount;

	@ApiModelProperty(value = "备注", example = "请尽快送达")
	private String remark;

	@ApiModelProperty(value = "用户名", example = "张三")
	private String userName;

	@ApiModelProperty(value = "手机号", example = "13800138000")
	private String phone;

	@ApiModelProperty(value = "地址", example = "北京市朝阳区")
	private String address;

	@ApiModelProperty(value = "收货人", example = "李四")
	private String consignee;

	@ApiModelProperty(value = "订单详情列表")
	private List<OrderDetail> orderDetails;

}