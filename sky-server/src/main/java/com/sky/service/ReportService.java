package com.sky.service;

import com.sky.vo.OrderReportVO;
import com.sky.vo.SalesTop10ReportVO;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;

public interface ReportService {

	/**
	 * 根据时间区间统计营业额
	 *
	 * @param beginTime 开始时间
	 * @param endTime   结束时间
	 * @return 营业额报告视图对象
	 */
	TurnoverReportVO getTurnover(LocalDate beginTime, LocalDate endTime);

	/**
	 * 根据时间区间统计用户数量
	 *
	 * @param begin 开始时间
	 * @param end   结束时间
	 * @return 用户报告视图对象
	 */
	UserReportVO getUserStatistics(LocalDate begin, LocalDate end);

	/**
	 * 根据时间区间统计订单数量
	 *
	 * @param begin 开始时间
	 * @param end   结束时间
	 * @return 订单报告视图对象
	 */
	OrderReportVO getOrderStatistics(LocalDate begin, LocalDate end);

	/**
	 * 查询指定时间区间内的销量排名top10
	 *
	 * @param begin 开始时间
	 * @param end   结束时间
	 * @return 销量前十报告视图对象
	 */
	SalesTop10ReportVO getSalesTop10(LocalDate begin, LocalDate end);

	/**
	 * 导出近30天的运营数据报表
	 *
	 * @param response 响应对象
	 **/
	void exportBusinessData(HttpServletResponse response);
}