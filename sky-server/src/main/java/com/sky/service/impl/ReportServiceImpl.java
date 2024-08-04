package com.sky.service.impl;

import com.sky.dao.OrderDAO;
import com.sky.dao.UserDAO;
import com.sky.dto.GoodsSalesDTO;
import com.sky.entity.Order;
import com.sky.mapper.OrderMapper;
import com.sky.service.ReportService;
import com.sky.service.WorkspaceService;
import com.sky.vo.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
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
	@Autowired
	private WorkspaceService workspaceService;

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
	@Override
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

	/**
	 * 导出近30天的运营数据报表
	 *
	 * @param response HttpServletResponse对象，用于写入导出的报表数据
	 **/
	@Override
	public void exportBusinessData(HttpServletResponse response) {
		LocalDate begin = LocalDate.now().minusDays(30);
		LocalDate end = LocalDate.now().minusDays(1);
		// 查询概览运营数据，提供给Excel模板文件
		BusinessDataVO businessData = workspaceService.getBusinessData(LocalDateTime.of(begin, LocalTime.MIN), LocalDateTime.of(end, LocalTime.MAX));
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("template/运营数据报表模板.xlsx");
		try {
			// 基于提供好的模板文件创建一个新的Excel表格对象
			assert inputStream != null;
			XSSFWorkbook excel = new XSSFWorkbook(inputStream);
			// 获得Excel文件中的一个Sheet页
			XSSFSheet sheet = excel.getSheet("Sheet1");

			sheet.getRow(1).getCell(1).setCellValue(begin + "至" + end);
			// 获得第4行
			XSSFRow row = sheet.getRow(3);
			// 获取单元格
			row.getCell(2).setCellValue(businessData.getTurnover());
			row.getCell(4).setCellValue(businessData.getOrderCompletionRate());
			row.getCell(6).setCellValue(businessData.getNewUsers());
			row = sheet.getRow(4);
			row.getCell(2).setCellValue(businessData.getValidOrderCount());
			row.getCell(4).setCellValue(businessData.getUnitPrice());
			for (int i = 0; i < 30; i++) {
				LocalDate date = begin.plusDays(i);
				// 准备明细数据
				businessData = workspaceService.getBusinessData(LocalDateTime.of(date, LocalTime.MIN), LocalDateTime.of(date, LocalTime.MAX));
				row = sheet.getRow(7 + i);
				row.getCell(1).setCellValue(date.toString());
				row.getCell(2).setCellValue(businessData.getTurnover());
				row.getCell(3).setCellValue(businessData.getValidOrderCount());
				row.getCell(4).setCellValue(businessData.getOrderCompletionRate());
				row.getCell(5).setCellValue(businessData.getUnitPrice());
				row.getCell(6).setCellValue(businessData.getNewUsers());
			}
			// 通过输出流将文件下载到客户端浏览器中
			ServletOutputStream out = response.getOutputStream();
			excel.write(out);
			// 关闭资源
			out.flush();
			out.close();
			excel.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
