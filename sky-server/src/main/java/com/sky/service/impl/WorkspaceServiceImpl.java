package com.sky.service.impl;

import com.sky.constant.StatusConstant;
import com.sky.dao.DishDAO;
import com.sky.dao.OrderDAO;
import com.sky.dao.SetmealDAO;
import com.sky.dao.UserDAO;
import com.sky.entity.Order;
import com.sky.service.WorkspaceService;
import com.sky.vo.BusinessDataVO;
import com.sky.vo.DishOverViewVO;
import com.sky.vo.OrderOverViewVO;
import com.sky.vo.SetmealOverViewVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class WorkspaceServiceImpl implements WorkspaceService {

	@Autowired
	private OrderDAO orderDAO;
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private SetmealDAO setmealDAO;
	@Autowired
	private DishDAO dishDAO;

	/**
	 * 根据订单状态查询订单数量
	 *
	 * @param map    包含查询参数的映射
	 * @param status 要查询的订单状态
	 * @return 指定状态的订单数量，如果没有找到则返回0
	 */
	private Number getOrderCountByStatus(Map<String, Object> map, Integer status) {
		map.put("status", status);
		return Optional.ofNullable(orderDAO.countByMap(map)).orElse(0);
	}

	/**
	 * 根据时间段统计营业数据
	 *
	 * @param begin 开始时间
	 * @param end   结束时间
	 * @return 统计的营业数据
	 * <p>
	 * 营业额：当日已完成订单的总金额
	 * 有效订单：当日已完成订单的数量
	 * 订单完成率：有效订单数 / 总订单数
	 * 平均客单价：营业额 / 有效订单数
	 * 新增用户：当日新增用户的数量
	 */
	@Override
	public BusinessDataVO getBusinessData(LocalDateTime begin, LocalDateTime end) {

		Map<String, Object> map = new HashMap<>();
		map.put("begin", begin);
		map.put("end", end);

		Integer totalOrderCount = (Integer) getOrderCountByStatus(map, null); // 查询总订单数

		map.put("status", Order.COMPLETED);

		Double turnover = Optional.ofNullable(orderDAO.sumByMap(map)).orElse(0.0); // 营业额
		Integer validOrderCount = (Integer) getOrderCountByStatus(map, Order.COMPLETED); // 有效订单数

		double unitPrice = 0.0; // 平均客单价
		double orderCompletionRate = 0.0; // 订单完成率
		if (totalOrderCount != 0 && validOrderCount != 0) {
			orderCompletionRate = validOrderCount.doubleValue() / totalOrderCount; // 订单完成率
			unitPrice = turnover / validOrderCount; // 平均客单价
		}

		Integer newUsers = userDAO.countByMap(map); // 新增用户数

		return BusinessDataVO.builder()
				.turnover(turnover)
				.validOrderCount(validOrderCount)
				.orderCompletionRate(orderCompletionRate)
				.unitPrice(unitPrice)
				.newUsers(newUsers)
				.build();
	}


	/**
	 * 查询订单管理数据
	 *
	 * @return OrderOverViewVO 包含订单概览数据的对象
	 */
	public OrderOverViewVO getOrderOverView() {
		Map<String, Object> map = new HashMap<>();
		map.put("begin", LocalDateTime.now().with(LocalTime.MIN)); // 今日开始时间

		Integer waitingOrder = (Integer) getOrderCountByStatus(map, Order.TO_BE_CONFIRMED); // 待接单
		Integer deliveredOrder = (Integer) getOrderCountByStatus(map, Order.CONFIRMED); // 待派送
		Integer completedOrder = (Integer) getOrderCountByStatus(map, Order.COMPLETED); // 已完成
		Integer cancelledOrder = (Integer) getOrderCountByStatus(map, Order.CANCELLED); // 已取消
		Integer allOrder = (Integer) getOrderCountByStatus(map, null); // 全部订单

		return OrderOverViewVO.builder()
				.waitingOrder(waitingOrder)
				.deliveredOrder(deliveredOrder)
				.completedOrder(completedOrder)
				.cancelledOrder(cancelledOrder)
				.allOrder(allOrder)
				.build();
	}

	/**
	 * 查询菜品总览
	 *
	 * @return DishOverViewVO 包含菜品总览数据的对象
	 */
	public DishOverViewVO getDishOverView() {
		Map<String, Object> map = new HashMap<>();

		map.put("status", StatusConstant.ENABLE); // 查询启用的菜品
		Integer sold = dishDAO.countByMap(map);

		map.put("status", StatusConstant.DISABLE); // 查询停售的菜品
		Integer discontinued = dishDAO.countByMap(map);

		return DishOverViewVO.builder()
				.sold(sold)
				.discontinued(discontinued)
				.build();
	}

	/**
	 * 查询套餐总览
	 *
	 * @return SetmealOverViewVO 包含套餐总览数据的对象
	 */
	public SetmealOverViewVO getSetmealOverView() {
		Map<String, Object> map = new HashMap<>();

		map.put("status", StatusConstant.ENABLE); // 查询启用的套餐
		Integer sold = setmealDAO.countByMap(map);

		map.put("status", StatusConstant.DISABLE); // 查询停售的套餐
		Integer discontinued = setmealDAO.countByMap(map);

		return SetmealOverViewVO.builder()
				.sold(sold)
				.discontinued(discontinued)
				.build();
	}
}