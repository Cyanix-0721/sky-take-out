package com.sky.controller.admin;

import com.sky.dto.OrderCancelDTO;
import com.sky.dto.OrderConfirmDTO;
import com.sky.dto.OrderPageQueryDTO;
import com.sky.dto.OrderRejectionDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.OrderService;
import com.sky.vo.OrderStatisticsVO;
import com.sky.vo.OrderVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 订单管理
 */
@RestController("adminOrderController")
@RequestMapping("/admin/order")
@Slf4j
@Api(tags = "订单管理接口")
public class OrderController {

	@Autowired
	private OrderService orderService;

	/**
	 * 订单搜索
	 *
	 * @param orderPageQueryDTO 查询条件
	 * @return 包含分页结果的Result对象
	 */
	@GetMapping("/conditionSearch")
	@ApiOperation("订单搜索")
	public Result<PageResult> conditionSearch(OrderPageQueryDTO orderPageQueryDTO) {
		PageResult pageResult = orderService.conditionSearch(orderPageQueryDTO);
		return Result.success(pageResult);
	}

	/**
	 * 各个状态的订单数量统计
	 *
	 * @return 包含订单统计信息的Result对象
	 */
	@GetMapping("/statistics")
	@ApiOperation("各个状态的订单数量统计")
	public Result<OrderStatisticsVO> statistics() {
		OrderStatisticsVO orderStatisticsVO = orderService.statistics();
		return Result.success(orderStatisticsVO);
	}

	/**
	 * 订单详情
	 *
	 * @param id 订单ID
	 * @return 包含订单详情的Result对象
	 */
	@GetMapping("/details/{id}")
	@ApiOperation("查询订单详情")
	public Result<OrderVO> details(@PathVariable("id") Long id) {
		OrderVO orderVO = orderService.details(id);
		return Result.success(orderVO);
	}

	/**
	 * 接单
	 *
	 * @param orderConfirmDTO 接单信息
	 * @return 包含操作结果的Result对象
	 */
	@PutMapping("/confirm")
	@ApiOperation("接单")
	public Result<OrderVO> confirm(@RequestBody OrderConfirmDTO orderConfirmDTO) {
		orderService.confirm(orderConfirmDTO);
		return Result.success();
	}

	/**
	 * 拒单
	 *
	 * @param orderRejectionDTO 拒单信息
	 * @return 包含操作结果的Result对象
	 * @throws Exception 如果拒单过程中发生错误
	 */
	@PutMapping("/rejection")
	@ApiOperation("拒单")
	public Result<OrderVO> rejection(@RequestBody OrderRejectionDTO orderRejectionDTO) throws Exception {
		orderService.rejection(orderRejectionDTO);
		return Result.success();
	}

	/**
	 * 取消订单
	 *
	 * @param orderCancelDTO 取消订单信息
	 * @return 包含操作结果的Result对象
	 * @throws Exception 如果取消过程中发生错误
	 */
	@PutMapping("/cancel")
	@ApiOperation("取消订单")
	public Result<Void> cancel(@RequestBody OrderCancelDTO orderCancelDTO) throws Exception {
		orderService.cancel(orderCancelDTO);
		return Result.success();
	}

	/**
	 * 派送订单
	 *
	 * @param id 订单ID
	 * @return 包含操作结果的Result对象
	 */
	@PutMapping("/delivery/{id}")
	@ApiOperation("派送订单")
	public Result<Void> delivery(@PathVariable("id") Long id) {
		orderService.delivery(id);
		return Result.success();
	}

	/**
	 * 完成订单
	 *
	 * @param id 订单ID
	 * @return 包含操作结果的Result对象
	 */
	@PutMapping("/complete/{id}")
	@ApiOperation("完成订单")
	public Result<Void> complete(@PathVariable("id") Long id) {
		orderService.complete(id);
		return Result.success();
	}
}