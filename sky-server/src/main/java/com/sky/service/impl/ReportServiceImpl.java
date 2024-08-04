package com.sky.service.impl;

import com.sky.dao.OrderDAO;
import com.sky.dao.UserDAO;
import com.sky.dto.GoodsSalesDTO;
import com.sky.entity.Order;
import com.sky.mapper.OrderMapper;
import com.sky.service.ReportService;
import com.sky.vo.OrderReportVO;
import com.sky.vo.SalesTop10ReportVO;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ReportServiceImpl implements ReportService {

	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private OrderDAO orderDAO;
	@Autowired
	private UserDAO userDAO;

	/**
	 * 初始化日期列表并添加从开始日期到结束日期的每一天
	 *
	 * @param begin 开始日期
	 * @param end   结束日期
	 * @return 包含从开始日期到结束日期的每一天的日期列表
	 */
	private List<LocalDate> initializeDateList(LocalDate begin, LocalDate end) {
		List<LocalDate> dateList = new ArrayList<>();
		dateList.add(begin);

		while (! begin.equals(end)) {
			begin = begin.plusDays(1); // 日期计算，获得指定日期后1天的日期
			dateList.add(begin);
		}

		return dateList;
	}

	/**
	 * 根据时间区间统计营业额
	 *
	 * @param begin 开始日期
	 * @param end   结束日期
	 * @return 包含日期列表和对应营业额列表的TurnoverReportVO对象
	 */
	public TurnoverReportVO getTurnover(LocalDate begin, LocalDate end) {
		// 初始化日期列表并添加开始日期
		List<LocalDate> dateList = initializeDateList(begin, end);

		// 使用流处理日期列表，计算每一天的营业额
		List<Double> turnoverList = dateList.stream().map(date -> {
			LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN); // 当天的开始时间
			LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX); // 当天的结束时间
			Map<String, Object> map = new HashMap<>();
			map.put("status", Order.COMPLETED); // 查询条件：订单状态为已完成
			map.put("begin", beginTime); // 查询条件：开始时间
			map.put("end", endTime); // 查询条件：结束时间
			Double turnover = orderDAO.sumByMap(map); // 查询数据库获取营业额
			return turnover == null ? 0.0 : turnover; // 处理空值情况
		}).collect(Collectors.toList());

		// 构建并返回包含日期列表和营业额列表的TurnoverReportVO对象
		return TurnoverReportVO.builder()
				.dateList(StringUtils.join(dateList, ",")) // 将日期列表转换为逗号分隔的字符串
				.turnoverList(StringUtils.join(turnoverList, ",")) // 将营业额列表转换为逗号分隔的��符串
				.build();
	}

	/**
	 * 根据时间区间统计用户数量
	 *
	 * @param begin 开始日期，格式为yyyy-MM-dd
	 * @param end   结束日期，格式为yyyy-MM-dd
	 * @return 包含用户数据的结果对象
	 */
	@Override
	public UserReportVO getUserStatistics(LocalDate begin, LocalDate end) {
		// 初始化日期列表并添加开始日期
		List<LocalDate> dateList = initializeDateList(begin, end);

		// 使用流处理日期列表，计算每一天的新增用户数和总用户数
		List<Integer> newUserList = dateList.stream().map(date -> {
			LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
			LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);
			// 新增用户数量 select count(id) from user where create_time > ? and create_time < ?
			return getUserCount(beginTime, endTime);
		}).collect(Collectors.toList());

		List<Integer> totalUserList = dateList.stream().map(date -> {
			LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);
			// 总用户数量 select count(id) from user where create_time < ?
			return getUserCount(null, endTime);
		}).collect(Collectors.toList());

		return UserReportVO.builder()
				.dateList(StringUtils.join(dateList, ","))
				.newUserList(StringUtils.join(newUserList, ","))
				.totalUserList(StringUtils.join(totalUserList, ","))
				.build();
	}

	/**
	 * 根据时间区间统计用户数量
	 *
	 * @param beginTime 开始时间，格式为yyyy-MM-ddTHH:mm:ss
	 * @param endTime   结束时间，格式为yyyy-MM-ddTHH:mm:ss
	 * @return 用户数量
	 */
	private Integer getUserCount(LocalDateTime beginTime, LocalDateTime endTime) {
		Map<String, Object> map = new HashMap<>();
		map.put("begin", beginTime);
		map.put("end", endTime);
		return userDAO.countByMap(map);
	}

	/**
	 * 根据时间区间统计订单数量
	 *
	 * @param begin 开始时间
	 * @param end   结束时间
	 * @return 订单报告视图对象
	 */
	@Override
	public OrderReportVO getOrderStatistics(LocalDate begin, LocalDate end) {
		// 初始化日期列表并添加开始日期
		List<LocalDate> dateList = initializeDateList(begin, end);

		// 使用流处理日期列表，计算每天的总订单数和有效订单数
		List<OrderCount> orderCounts = dateList.stream().map(date -> {
			LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
			LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);
			// 查询每天的总订单数 select count(id) from orders where order_time > ? and order_time < ?
			Integer orderCount = getOrderCount(beginTime, endTime, null);
			// 查询每天的有效订单数 select count(id) from orders where order_time > ? and order_time < ? and status = ?
			Integer validOrderCount = getOrderCount(beginTime, endTime, Order.COMPLETED);
			return new OrderCount(orderCount, validOrderCount);
		}).collect(Collectors.toList());

		// 提取总订单数和有效订单数列表
		List<Integer> orderCountList = orderCounts.stream().map(OrderCount::getOrderCount).collect(Collectors.toList());
		List<Integer> validOrderCountList = orderCounts.stream().map(OrderCount::getValidOrderCount).collect(Collectors.toList());

		// 计算时间区间内的总订单数和总有效订单数
		Integer totalOrderCount = orderCountList.stream().reduce(Integer::sum).orElse(0);
		Integer validOrderCount = validOrderCountList.stream().reduce(Integer::sum).orElse(0);

		// 计算订单完成率
		Double orderCompletionRate = totalOrderCount != 0 ? validOrderCount.doubleValue() / totalOrderCount : 0.0;

		return OrderReportVO.builder()
				.dateList(StringUtils.join(dateList, ","))
				.orderCountList(StringUtils.join(orderCountList, ","))
				.validOrderCountList(StringUtils.join(validOrderCountList, ","))
				.totalOrderCount(totalOrderCount)
				.validOrderCount(validOrderCount)
				.orderCompletionRate(orderCompletionRate)
				.build();
	}

	/**
	 * 辅助类，用于存储每天的订单数和有效订单数
	 */
	@Getter
	@AllArgsConstructor
	private static class OrderCount {
		private final Integer orderCount;
		private final Integer validOrderCount;
	}

	/**
	 * 根据时间区间统计指定状态的订单数量
	 *
	 * @param beginTime 开始时间，格式为yyyy-MM-ddTHH:mm:ss
	 * @param endTime   结束时间，格式为yyyy-MM-ddTHH:mm:ss
	 * @param status    订单状态
	 * @return 订单数量
	 */
	private Integer getOrderCount(LocalDateTime beginTime, LocalDateTime endTime, Integer status) {
		Map<String, Object> map = new HashMap<>();
		map.put("status", status);
		map.put("begin", beginTime);
		map.put("end", endTime);
		return orderDAO.countByMap(map);
	}

	/**
	 * 查询指定时间区间内的销量排名top10
	 *
	 * @param begin 开始时间
	 * @param end   结束时间
	 * @return 销量前十报告视图对象
	 */
	@Override
	public SalesTop10ReportVO getSalesTop10(LocalDate begin, LocalDate end) {
		LocalDateTime beginTime = LocalDateTime.of(begin, LocalTime.MIN);
		LocalDateTime endTime = LocalDateTime.of(end, LocalTime.MAX);
		List<GoodsSalesDTO> goodsSalesDTOList = orderMapper.getSalesTop10(beginTime, endTime);

		String nameList = StringUtils.join(goodsSalesDTOList.stream()
				.map(GoodsSalesDTO::getName)
				.collect(Collectors.toList()), ",");
		String numberList = StringUtils.join(goodsSalesDTOList.stream()
				.map(GoodsSalesDTO::getNumber)
				.collect(Collectors.toList()), ",");

		return SalesTop10ReportVO.builder()
				.nameList(nameList)
				.numberList(numberList)
				.build();
	}
}
