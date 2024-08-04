package com.sky.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sky.dto.OrderPageQueryDTO;
import com.sky.entity.Order;
import com.sky.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

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
}
