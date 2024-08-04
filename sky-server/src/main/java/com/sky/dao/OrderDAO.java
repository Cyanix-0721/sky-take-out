package com.sky.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sky.dto.GoodsSalesDTO;
import com.sky.dto.OrderPageQueryDTO;
import com.sky.entity.Order;
import com.sky.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class OrderDAO {
	@Autowired
	private OrderMapper orderMapper;

	public Page<Order> pageQuery(OrderPageQueryDTO orderQueryDTO) {
		Page<Order> page = new Page<>(orderQueryDTO.getPage(), orderQueryDTO.getPageSize());
		LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<>();

		queryWrapper
				.eq(orderQueryDTO.getUserId() != null, Order::getUserId, orderQueryDTO.getUserId())
				.like(orderQueryDTO.getNumber() != null && ! orderQueryDTO.getNumber().isEmpty(), Order::getNumber, orderQueryDTO.getNumber())
				.eq(orderQueryDTO.getStatus() != null, Order::getStatus, orderQueryDTO.getStatus())
				.like(orderQueryDTO.getPhone() != null && ! orderQueryDTO.getPhone().isEmpty(), Order::getPhone, orderQueryDTO.getPhone())
				.ge(orderQueryDTO.getBeginTime() != null, Order::getOrderTime, orderQueryDTO.getBeginTime())
				.le(orderQueryDTO.getEndTime() != null, Order::getOrderTime, orderQueryDTO.getEndTime())
				.orderByDesc(Order::getOrderTime);

		return orderMapper.selectPage(page, queryWrapper);
	}

	/**
	 * 根据状态和下单时间查询订单
	 *
	 * @param status    订单状态
	 * @param orderTime 下单时间
	 * @return 符合条件的订单列表
	 */
	public List<Order> getByStatusAndOrderTimeLT(Integer status, LocalDateTime orderTime) {
		LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<>();

		queryWrapper.eq(Order::getStatus, status)
				.lt(Order::getOrderTime, orderTime);

		return orderMapper.selectList(queryWrapper);
	}

	/**
	 * 根据动态条件统计营业额
	 *
	 * @param map 包含查询条件的映射，其中可能包含以下键：
	 *            - "status" (Integer): 订单状态
	 *            - "begin" (LocalDateTime): 开始时间
	 *            - "end" (LocalDateTime): 结束时间
	 * @return 符合条件的订单总金额
	 */
	public Double sumByMap(Map<String, Object> map) {
		return orderMapper.selectList(LQW_stat_beg_end(map))
				.stream()
				.mapToDouble(order -> order.getAmount().doubleValue())
				.sum();
	}

	public Integer countByMap(Map<String, Object> map) {
		return Math.toIntExact(orderMapper.selectCount(LQW_stat_beg_end(map)));
	}

	public LambdaQueryWrapper<Order> LQW_stat_beg_end(Map<String, Object> map) {
		LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<>();

		queryWrapper
				.eq(map.get("status") != null, Order::getStatus, map.get("status"))
				.ge(map.get("begin") != null, Order::getOrderTime, map.get("begin"))
				.le(map.get("end") != null, Order::getOrderTime, map.get("end"));
		return queryWrapper;
	}
}
