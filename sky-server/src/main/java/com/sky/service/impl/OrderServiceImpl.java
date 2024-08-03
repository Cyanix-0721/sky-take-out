package com.sky.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sky.constant.MessageConstant;
import com.sky.context.BaseContext;
import com.sky.dao.ShoppingCartDAO;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.entity.AddressBook;
import com.sky.entity.Order;
import com.sky.entity.OrderDetail;
import com.sky.entity.ShoppingCart;
import com.sky.exception.AddressBookBusinessException;
import com.sky.exception.ShoppingCartBusinessException;
import com.sky.mapper.AddressBookMapper;
import com.sky.mapper.OrderDetailMapper;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.ShoppingCartMapper;
import com.sky.service.OrderService;
import com.sky.vo.OrderSubmitVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 订单服务实现类
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private OrderDetailMapper orderDetailMapper;
	@Autowired
	private ShoppingCartMapper shoppingCartMapper;
	@Autowired
	private AddressBookMapper addressBookMapper;
	@Autowired
	private ShoppingCartDAO shoppingCartDAO;

	/**
	 * 用户下单
	 *
	 * @param ordersSubmitDTO 包含订单提交信息的DTO对象
	 * @return 返回订单提交视图对象
	 */
	@Override
	@Transactional
	public OrderSubmitVO submitOrder(OrdersSubmitDTO ordersSubmitDTO) {
		// 异常情况的处理（收货地址为空、超出配送范围、购物车为空）
		AddressBook addressBook = addressBookMapper.selectById(ordersSubmitDTO.getAddressBookId());
		log.info("获取收货地址:{}", addressBook);
		if (addressBook == null) {
			throw new AddressBookBusinessException(MessageConstant.ADDRESS_BOOK_IS_NULL);
		}

		Long userId = BaseContext.getCurrentId();
		log.info("获取当前用户ID:{}", userId);
		ShoppingCart shoppingCart = ShoppingCart.builder()
				.userId(userId)
				.build();

		// 查询当前用户的购物车数据
		List<ShoppingCart> shoppingCartList = shoppingCartDAO.list(shoppingCart);
		log.info("获取购物车:{}", shoppingCartList);
		if (shoppingCartList == null || shoppingCartList.isEmpty()) {
			throw new ShoppingCartBusinessException(MessageConstant.SHOPPING_CART_IS_NULL);
		}

		// 构造订单数据
		Order order = Order.builder()
				.phone(addressBook.getPhone())
				.address(addressBook.getDetail())
				.consignee(addressBook.getConsignee())
				.number(String.valueOf(System.currentTimeMillis()))
				.userId(userId)
				.status(Order.PENDING_PAYMENT)
				.payStatus(Order.UN_PAID)
				.orderTime(LocalDateTime.now())
				.build();
		BeanUtils.copyProperties(ordersSubmitDTO, order);
		log.info("构造订单:{}", order);
		orderMapper.insert(order);

		// 订单明细数据
		List<OrderDetail> orderDetailList = shoppingCartList.stream()
				.map(cart -> {
					OrderDetail orderDetail = new OrderDetail();
					BeanUtils.copyProperties(cart, orderDetail);
					orderDetail.setOrderId(order.getId());
					return orderDetail;
				})
				.collect(Collectors.toList());
		log.info("构造订单明细:{}", orderDetailList);
		orderDetailMapper.insert(orderDetailList);

		// 清理购物车中的数据
		shoppingCartMapper.delete(new LambdaQueryWrapper<ShoppingCart>().eq(ShoppingCart::getUserId, userId));

		// 封装返回结果
		return OrderSubmitVO.builder()
				.id(order.getId())
				.orderNumber(order.getNumber())
				.orderAmount(order.getAmount())
				.orderTime(order.getOrderTime())
				.build();
	}
}