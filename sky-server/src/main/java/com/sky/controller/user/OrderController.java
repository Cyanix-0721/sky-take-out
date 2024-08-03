package com.sky.controller.user;

import com.sky.dto.OrderPaymentDTO;
import com.sky.dto.OrderSubmitDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.OrderService;
import com.sky.vo.OrderPaymentVO;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 订单控制器类，处理与订单相关的请求
 */
@RestController("userOrderController")
@RequestMapping("/user/order")
@Slf4j
@Api(tags = "C端-订单接口")
public class OrderController {

	@Autowired
	private OrderService orderService;

	/**
	 * 用户下单
	 *
	 * @param ordersSubmitDTO 包含订单提交信息的DTO对象
	 * @return 返回订单提交视图对象
	 */
	@PostMapping("/submit")
	@ApiOperation("用户下单")
	public Result<OrderSubmitVO> submit(@RequestBody OrderSubmitDTO ordersSubmitDTO) {
		log.info("用户下单：{}", ordersSubmitDTO);
		OrderSubmitVO orderSubmitVO = orderService.submitOrder(ordersSubmitDTO);
		return Result.success(orderSubmitVO);
	}

	/**
	 * 订单支付
	 *
	 * @param ordersPaymentDTO 包含订单支付信息的DTO对象
	 * @return 返回订单支付视图对象
	 * @throws Exception 如果支付过程中发生错误
	 */
	@PutMapping("/payment")
	@ApiOperation("订单支付")
	public Result<OrderPaymentVO> payment(@RequestBody OrderPaymentDTO ordersPaymentDTO) throws Exception {
		log.info("订单支付：{}", ordersPaymentDTO);
		OrderPaymentVO orderPaymentVO = orderService.payment(ordersPaymentDTO);
		log.info("生成预支付交易单：{}", orderPaymentVO);
		return Result.success(orderPaymentVO);
	}

	/**
	 * 历史订单查询
	 *
	 * @param page     当前页码
	 * @param pageSize 每页显示的记录数
	 * @param status   订单状态 1待付款 2待接单 3已接单 4派送中 5已完成 6已取消
	 * @return 包含分页结果的Result对象
	 */
	//TODO 超时订单仍然属于待付款订单，可考虑引入MQ，定时任务，定时检查订单状态，超时则取消订单
	@GetMapping("/historyOrders")
	@ApiOperation("历史订单查询")
	public Result<PageResult> page(int page, int pageSize, Integer status) {
		// 调用订单服务的分页查询方法，获取分页结果
		PageResult pageResult = orderService.pageQuery4User(page, pageSize, status);
		// 返回包含分页结果的成功响应
		return Result.success(pageResult);
	}

	/**
	 * 查询订单详情
	 *
	 * @param id 订单ID
	 * @return 包含订单详情的Result对象
	 */
	@GetMapping("/orderDetail/{id}")
	@ApiOperation("查询订单详情")
	public Result<OrderVO> details(@PathVariable("id") Long id) {
		// 调用订单服务的details方法，获取订单详情
		OrderVO orderVO = orderService.details(id);
		// 返回包含订单详情的成功响应
		return Result.success(orderVO);
	}

	/**
	 * 用户取消订单
	 *
	 * @param id 订单ID
	 * @return 包含取消结果的Result对象
	 * @throws Exception 如果取消过程中发生错误
	 */
	@PutMapping("/cancel/{id}")
	@ApiOperation("取消订单")
	public Result<Void> cancel(@PathVariable("id") Long id) throws Exception {
		// 调用订单服务的userCancelById方法，取消订单
		orderService.userCancelById(id);
		// 返回包含取消结果的成功响应
		return Result.success();
	}

	/**
	 * 再来一单
	 *
	 * @param id 订单ID
	 * @return 包含再来一单结果的Result对象
	 */
	@PostMapping("/repetition/{id}")
	@ApiOperation("再来一单")
	public Result<Void> repetition(@PathVariable Long id) {
		// 调用订单服务的repetition方法，再来一单
		orderService.repetition(id);
		// 返回包含再来一单结果的成功响应
		return Result.success();
	}
}