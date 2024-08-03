package com.sky.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sky.entity.OrderDetail;
import com.sky.mapper.OrderDetailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderDetailDAO {
	@Autowired
	private OrderDetailMapper orderDetailMapper;

	public List<OrderDetail> getByOrderId(Long orderId) {
		return orderDetailMapper.selectList(new LambdaQueryWrapper<OrderDetail>()
				.eq(OrderDetail::getOrderId, orderId));
	}
}
