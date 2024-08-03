package com.sky.service;

import com.sky.dto.*;
import com.sky.vo.OrderSubmitVO;

public interface OrderService {

	/**
	 * 用户下单
	 *
	 * @param ordersSubmitDTO 包含订单提交信息的DTO对象
	 * @return 返回订单提交视图对象
	 */
	OrderSubmitVO submitOrder(OrdersSubmitDTO ordersSubmitDTO);
}