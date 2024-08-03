package com.sky.vo;

import com.sky.entity.Order;
import com.sky.entity.OrderDetail;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "订单视图对象")
public class OrderVO extends Order implements Serializable {

	@ApiModelProperty(value = "订单菜品信息", example = "宫保鸡丁, 红烧肉")
	private String orderDishes;

	@ApiModelProperty(value = "订单详情列表")
	private List<OrderDetail> orderDetailList;
}