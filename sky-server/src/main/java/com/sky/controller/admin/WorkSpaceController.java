package com.sky.controller.admin;

import com.sky.result.Result;
import com.sky.service.WorkspaceService;
import com.sky.vo.BusinessDataVO;
import com.sky.vo.DishOverViewVO;
import com.sky.vo.OrderOverViewVO;
import com.sky.vo.SetmealOverViewVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * 工作台控制器，提供与工作台相关的接口
 */
@RestController
@RequestMapping("/admin/workspace")
@Slf4j
@Api(tags = "工作台相关接口")
public class WorkSpaceController {

	@Autowired
	private WorkspaceService workspaceService;

	/**
	 * 工作台今日数据查询
	 *
	 * @return 包含今日营业数据的结果对象
	 */
	@GetMapping("/businessData")
	@ApiOperation("工作台今日数据查询")
	public Result<BusinessDataVO> businessData() {
		// 获得当天的开始时间
		LocalDateTime begin = LocalDateTime.now().with(LocalTime.MIN);
		// 获得当天的结束时间
		LocalDateTime end = LocalDateTime.now().with(LocalTime.MAX);

		BusinessDataVO businessDataVO = workspaceService.getBusinessData(begin, end);
		return Result.success(businessDataVO);
	}

	/**
	 * 查询订单管理数据
	 *
	 * @return 包含订单管理数据概览的结果对象
	 */
	@GetMapping("/overviewOrders")
	@ApiOperation("查询订单管理数据")
	public Result<OrderOverViewVO> orderOverView() {
		return Result.success(workspaceService.getOrderOverView());
	}

	/**
	 * 查询菜品总览
	 *
	 * @return 包含菜品总览数据的结果对象
	 */
	@GetMapping("/overviewDishes")
	@ApiOperation("查询菜品总览")
	public Result<DishOverViewVO> dishOverView() {
		return Result.success(workspaceService.getDishOverView());
	}

	/**
	 * 查询套餐总览
	 *
	 * @return 包含套餐总览数据的结果对象
	 */
	@GetMapping("/overviewSetmeals")
	@ApiOperation("查询套餐总览")
	public Result<SetmealOverViewVO> setmealOverView() {
		return Result.success(workspaceService.getSetmealOverView());
	}
}