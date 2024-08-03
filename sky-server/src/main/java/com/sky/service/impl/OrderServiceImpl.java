package com.sky.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sky.constant.MessageConstant;
import com.sky.context.BaseContext;
import com.sky.dao.OrderDAO;
import com.sky.dao.OrderDetailDAO;
import com.sky.dao.ShoppingCartDAO;
import com.sky.dto.OrderPageQueryDTO;
import com.sky.dto.OrderPaymentDTO;
import com.sky.dto.OrderSubmitDTO;
import com.sky.entity.*;
import com.sky.exception.AddressBookBusinessException;
import com.sky.exception.OrderBusinessException;
import com.sky.exception.ShoppingCartBusinessException;
import com.sky.mapper.*;
import com.sky.result.PageResult;
import com.sky.service.OrderService;
import com.sky.utils.WeChatPayUtil;
import com.sky.vo.OrderPaymentVO;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
	private ShoppingCartDAO shoppingCartDAO;
	@Autowired
	private AddressBookMapper addressBookMapper;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private WeChatPayUtil weChatPayUtil;
	@Autowired
	private OrderDAO orderDAO;
	@Autowired
	private OrderDetailDAO orderDetailDAO;

	/**
	 * 用户下单
	 *
	 * @param ordersSubmitDTO 包含订单提交信息的DTO对象
	 * @return 返回订单提交视图对象
	 */
	@Override
	@Transactional
	public OrderSubmitVO submitOrder(OrderSubmitDTO ordersSubmitDTO) {
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

	/**
	 * 处理订单支付
	 *
	 * @param ordersPaymentDTO 包含订单支付信息的DTO对象
	 * @return 返回订单支付视图对象
	 * @throws Exception 如果支付过程中发生错误
	 */
	@Override
	public OrderPaymentVO payment(OrderPaymentDTO ordersPaymentDTO) throws Exception {
//		// 当前登录用户id
//		Long userId = BaseContext.getCurrentId();
//		User user = userMapper.selectById(userId);
//
//		JsonObject jsonObject;
//		// 调用微信支付接口，生成预支付交易单
//		try {
//			jsonObject = weChatPayUtil.pay(
//					ordersPaymentDTO.getOrderNumber(), // 商户订单号
//					new BigDecimal("0.01"), // 支付金额，单位 元
//					"苍穹外卖订单", // 商品描述
//					user.getOpenid() // 微信用户的openid
//			);
//		} catch (Exception e) {
//			log.error("微信支付接口调用失败", e);
//			throw new OrderBusinessException("微信支付接口调用失败");
//		}
//
//		// 检查订单是否已支付
//		if ("ORDERPAID".equals(jsonObject.getString("code"))) {
//			throw new OrderBusinessException("该订单已支付");
//		}

		// 正常流程返回待支付到前端，支付后调用 PayNotifyController的 /notify/paySuccess 接口
		// 直接调用支付成功方法，模拟支付成功并返回到前端
		paySuccess(ordersPaymentDTO.getOrderNumber());

		// 创建一个新的JSONObject对象，用于存储支付结果
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("code", "ORDERPAID"); // 设置支付结果码为"ORDERPAID"

		// 将支付结果转换为OrderPaymentVO对象
		OrderPaymentVO vo = jsonObject.toJavaObject(OrderPaymentVO.class);
		vo.setPackageStr(jsonObject.getString("package")); // 设置packageStr属性

		// 返回OrderPaymentVO对象
		return vo;
	}

	/**
	 * 支付成功后，修改订单状态
	 *
	 * @param outTradeNo 支付交易号
	 */
	@Override
	public void paySuccess(String outTradeNo) {
		// 根据订单号查询当前用户的订单
		Order ordersDB = orderMapper.selectOne(new LambdaQueryWrapper<Order>()
				.eq(Order::getNumber, outTradeNo) // 订单号
				.eq(Order::getUserId, BaseContext.getCurrentId())); // 用户id

		// 根据订单id更新订单的状态、支付方式、支付状态、结账时间
		Order order = Order.builder()
				.id(ordersDB.getId())
				.status(Order.TO_BE_CONFIRMED) // 待接单
				.payStatus(Order.PAID) // 已支付
				.checkoutTime(LocalDateTime.now()) // 结账时间
				.build();

		orderMapper.updateById(order);
	}

	/**
	 * 用户端订单分页查询
	 *
	 * @param pageNum  当前页码
	 * @param pageSize 每页显示的记录数
	 * @param status   订单状态 1待付款 2待接单 3已接单 4派送中 5已完成 6已取消
	 * @return 包含分页结果的PageResult对象
	 */
	@Override
	public PageResult pageQuery4User(int pageNum, int pageSize, Integer status) {
		// 设置分页
		Page<Order> page = new Page<>(pageNum, pageSize);

		// 构造查询条件
		LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(status != null, Order::getStatus, status)
				.eq(Order::getUserId, BaseContext.getCurrentId())
				.orderByDesc(Order::getOrderTime);

		// 分页条件查询
		Page<Order> orderPage = orderMapper.selectPage(page, queryWrapper);

		// 查询出订单明细，并封装入OrderVO进行响应
		List<OrderVO> list = orderPage.getRecords().stream().map(order -> {

			Long orderId = order.getId();
			// 查询订单明细
			List<OrderDetail> orderDetails = orderDetailDAO.getByOrderId(orderId);

			// 把 Order 对象的属性拷贝到 OrderVO 对象
			OrderVO orderVO = new OrderVO();
			BeanUtils.copyProperties(order, orderVO);
			// 把订单明细列表设置到 OrderVO 对象中
			orderVO.setOrderDetailList(orderDetails);

			return orderVO;
		}).collect(Collectors.toList());

		return new PageResult(orderPage.getTotal(), list);
	}

	/**
	 * 查询订单详情
	 *
	 * @param id 订单ID
	 * @return 包含订单详情的OrderVO对象
	 */
	@Override
	public OrderVO details(Long id) {
		// 根据id查询订单
		Order order = orderMapper.selectById(id);

		// 查询该订单对应的菜品/套餐明细
		List<OrderDetail> orderDetailList = orderDetailDAO.getByOrderId(order.getId());

		// 将该订单及其详情封装到OrderVO并返回
		OrderVO orderVO = new OrderVO();
		BeanUtils.copyProperties(order, orderVO);
		orderVO.setOrderDetailList(orderDetailList);

		return orderVO;
	}

	/**
	 * 用户取消订单
	 *
	 * @param id 订单ID
	 * @throws Exception 如果取消过程中发生错误
	 */
	@Override
	public void userCancelById(Long id) throws Exception {
		// 根据id查询订单
		Order orderDB = Optional.ofNullable(orderMapper.selectById(id))
				.orElseThrow(() -> new OrderBusinessException(MessageConstant.ORDER_NOT_FOUND));

		// 订单状态 1待付款 2待接单 3已接单 4派送中 5已完成 6已取消
		if (orderDB.getStatus() > 2) {
			throw new OrderBusinessException(MessageConstant.ORDER_STATUS_ERROR);
		}

		// 订单处于待接单状态下取消，需要进行退款
		if (orderDB.getStatus().equals(Order.TO_BE_CONFIRMED)) {
			// 调用微信支付退款接口
			weChatPayUtil.refund(
					orderDB.getNumber(), // 商户订单号
					orderDB.getNumber(), // 商户退款单号
					new BigDecimal("0.01"), // 退款金额，单位 元
					new BigDecimal("0.01") // 原订单金额
			);
		}

		// 更新订单状态、取消原因、取消时间
		Order order = Order.builder()
				.id(orderDB.getId())
				.status(Order.CANCELLED)
				.cancelReason("用户取消")
				.cancelTime(LocalDateTime.now())
				//如果订单状态为待接单，支付状态为退款，否则支付状态不变
				.payStatus(orderDB.getStatus().equals(Order.TO_BE_CONFIRMED) ? Order.REFUND : orderDB.getPayStatus())
				.build();

		orderMapper.updateById(order);
	}

	/**
	 * 再来一单
	 *
	 * @param id 订单ID
	 */
	@Override
	public void repetition(Long id) {
		// 根据订单id查询当前订单详情
		List<OrderDetail> orderDetailList = orderDetailDAO.getByOrderId(id);

		// 将订单详情对象转换为购物车对象
		List<ShoppingCart> shoppingCartList = orderDetailList.stream().map(x -> Optional.ofNullable(x)
						.map(orderDetail -> {
							ShoppingCart shoppingCart = ShoppingCart.builder()
									.userId(BaseContext.getCurrentId())
									.createTime(LocalDateTime.now())
									.build();
							BeanUtils.copyProperties(orderDetail, shoppingCart, "id");
							return shoppingCart;
						})
						.orElseThrow(() -> new OrderBusinessException(MessageConstant.ORDER_NOT_FOUND)))
				.collect(Collectors.toList());

		// 将购物车对象批量添加到数据库
		shoppingCartMapper.insert(shoppingCartList);
	}
}