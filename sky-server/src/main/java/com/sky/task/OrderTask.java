package com.sky.task;

import com.sky.dao.OrderDAO;
import com.sky.entity.Order;
import com.sky.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

/**
 * 自定义定时任务，实现订单状态定时处理
 */
@Component
@Slf4j
public class OrderTask {

	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private OrderDAO orderDAO;

	/**
	 * 处理支付超时订单
	 * <p>
	 * 定时任务，每分钟执行一次，处理支付超时的订单，将其状态更新为已取消
	 */
	@Scheduled(cron = "0 * * * * ?")
	public void processTimeoutOrder() {
		log.info("处理支付超时订单：{}", new Date());

		LocalDateTime time = LocalDateTime.now().plusMinutes(- 15);

		// select * from order where status = 1 and order_time < 当前时间-15分钟
		Optional.ofNullable(orderDAO.getByStatusAndOrderTimeLT(Order.PENDING_PAYMENT, time))
				.ifPresent(orderList -> {
					if (! orderList.isEmpty()) {
						orderList.forEach(order -> {
							order.setStatus(Order.CANCELLED);
							order.setCancelReason("支付超时，自动取消");
							order.setCancelTime(LocalDateTime.now());
							orderMapper.updateById(order);
						});
					}
				});
	}

	/**
	 * 处理“派送中”状态的订单
	 * <p>
	 * 定时任务，每天凌晨1点执行一次，处理派送中超过1小时的订单，将其状态更新为已完成
	 */
	@Scheduled(cron = "0 0 1 * * ?")
	public void processDeliveryOrder() {
		log.info("处理派送中订单：{}", new Date());

		// select * from orders where status = 4 and order_time < 当前时间-1小时
		LocalDateTime time = LocalDateTime.now().plusMinutes(- 60);

		Optional.ofNullable(orderDAO.getByStatusAndOrderTimeLT(Order.DELIVERY_IN_PROGRESS, time))
				.ifPresent(orderList -> {
					if (! orderList.isEmpty()) {
						orderList.forEach(order -> {
							order.setStatus(Order.COMPLETED);
							orderMapper.updateById(order);
						});
					}
				});
	}

}