package com.sky.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "订单实体类")
@TableName("`order`")
public class Order implements Serializable {

	/**
	 * 订单状态 1待付款 2待接单 3已接单 4派送中 5已完成 6已取消
	 */
	public static final Integer PENDING_PAYMENT = 1;
	public static final Integer TO_BE_CONFIRMED = 2;
	public static final Integer CONFIRMED = 3;
	public static final Integer DELIVERY_IN_PROGRESS = 4;
	public static final Integer COMPLETED = 5;
	public static final Integer CANCELLED = 6;

	/**
	 * 支付状态 0未支付 1已支付 2退款
	 */
	public static final Integer UN_PAID = 0;
	public static final Integer PAID = 1;
	public static final Integer REFUND = 2;


	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "订单ID", example = "1")
	private Long id;

	@ApiModelProperty(value = "订单号", example = "202301010001")
	private String number;

	@ApiModelProperty(value = "订单状态 1待付款 2待接单 3已接单 4派送中 5已完成 6已取消", example = "1", allowableValues = "1, 2, 3, 4, 5, 6")
	private Integer status;

	@TableField(fill = FieldFill.INSERT_UPDATE)
	@ApiModelProperty(value = "下单用户ID", example = "1")
	private Long userId;

	@ApiModelProperty(value = "地址ID", example = "1")
	private Long addressBookId;

	@ApiModelProperty(value = "下单时间", example = "2023-01-01T12:00:00")
	private LocalDateTime orderTime;

	@ApiModelProperty(value = "结账时间", example = "2023-01-01T12:30:00")
	private LocalDateTime checkoutTime;

	@ApiModelProperty(value = "支付方式 1微信 2支付宝", example = "1", allowableValues = "1, 2")
	private Integer payMethod;

	@ApiModelProperty(value = "支付状态 0未支付 1已支付 2退款", example = "0", allowableValues = "0, 1, 2")
	private Integer payStatus;

	@ApiModelProperty(value = "实收金额", example = "59.98")
	private BigDecimal amount;

	@ApiModelProperty(value = "备注", example = "请尽快送达")
	private String remark;

	@ApiModelProperty(value = "手机号", example = "13800138000")
	private String phone;

	@ApiModelProperty(value = "地址", example = "北京市朝阳区")
	private String address;

	@ApiModelProperty(value = "用户名", example = "张三")
	private String userName;

	@ApiModelProperty(value = "收货人", example = "李四")
	private String consignee;

	@ApiModelProperty(value = "订单取消原因", example = "用户取消")
	private String cancelReason;

	@ApiModelProperty(value = "订单拒绝原因", example = "商家拒绝")
	private String rejectionReason;

	@ApiModelProperty(value = "订单取消时间", example = "2023-01-01T13:00:00")
	private LocalDateTime cancelTime;

	@ApiModelProperty(value = "预计送达时间", example = "2023-01-01T14:00:00")
	private LocalDateTime estimatedDeliveryTime;

	@ApiModelProperty(value = "配送状态 1立即送出 0选择具体时间", example = "1", allowableValues = "0, 1")
	private Integer deliveryStatus;

	@ApiModelProperty(value = "送达时间", example = "2023-01-01T14:30:00")
	private LocalDateTime deliveryTime;

	@ApiModelProperty(value = "打包费", example = "5")
	private int packAmount;

	@ApiModelProperty(value = "餐具数量", example = "2")
	private int tablewareNumber;

	@ApiModelProperty(value = "餐具数量状态 1按餐量提供 0选择具体数量", example = "1", allowableValues = "0, 1")
	private Integer tablewareStatus;

	@Version
	private Integer version;
}
