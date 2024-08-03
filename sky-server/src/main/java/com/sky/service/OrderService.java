package com.sky.service;

import com.sky.dto.*;
import com.sky.result.PageResult;
import com.sky.vo.OrderPaymentVO;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;

public interface OrderService {

	/**
	 * 用户下单
	 *
	 * @param ordersSubmitDTO 包含订单提交信息的DTO对象
	 * @return 返回订单提交视图对象
	 */
	OrderSubmitVO submitOrder(OrderSubmitDTO ordersSubmitDTO);

	/**
	 * 订单支付
	 *
	 * @param ordersPaymentDTO 包含订单支付信息的DTO对象
	 * @return 返回订单支付视图对象
	 * @throws Exception 如果支付过程中发生错误
	 */
	OrderPaymentVO payment(OrderPaymentDTO ordersPaymentDTO) throws Exception;

	/**
	 * 支付成功，修改订单状态
	 *
	 * @param outTradeNo 支付交易号
	 */
	void paySuccess(String outTradeNo);

	/**
	 * 用户端订单分页查询
	 *
	 * @param page     当前页码
	 * @param pageSize 每页显示的记录数
	 * @param status   订单状态 1待付款 2待接单 3已接单 4派送中 5已完成 6已取消
	 * @return 包含分页结果的PageResult对象
	 */
	PageResult pageQuery4User(int page, int pageSize, Integer status);

	/**
	 * 查询订单详情
	 *
	 * @param id 订单ID
	 * @return 包含订单详情的OrderVO对象
	 */
	OrderVO details(Long id);

	/**
	 * 用户取消订单
	 *
	 * @param id 订单ID
	 * @throws Exception 如果取消过程中发生错误
	 */
	void userCancelById(Long id) throws Exception;

	/**
	 * 再来一单
	 *
	 * @param id 订单ID
	 */
	void repetition(Long id);
}