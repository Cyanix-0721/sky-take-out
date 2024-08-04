package com.sky.service;

import com.sky.vo.BusinessDataVO;
import com.sky.vo.DishOverViewVO;
import com.sky.vo.OrderOverViewVO;
import com.sky.vo.SetmealOverViewVO;

import java.time.LocalDateTime;

/**
 * WorkspaceService interface for managing workspace-related operations.
 */
public interface WorkspaceService {

	/**
	 * 根据时间段统计营业数据
	 *
	 * @param begin 开始时间
	 * @param end   结束时间
	 * @return 统计的营业数据
	 */
	BusinessDataVO getBusinessData(LocalDateTime begin, LocalDateTime end);

	/**
	 * 查询订单管理数据
	 *
	 * @return 订单管理数据概览
	 */
	OrderOverViewVO getOrderOverView();

	/**
	 * 查询菜品总览
	 *
	 * @return 菜品总览数据
	 */
	DishOverViewVO getDishOverView();

	/**
	 * 查询套餐总览
	 *
	 * @return 套餐总览数据
	 */
	SetmealOverViewVO getSetmealOverView();

}