package com.sky.controller.admin;

import com.sky.result.Result;
import com.sky.service.ReportService;
import com.sky.vo.OrderReportVO;
import com.sky.vo.SalesTop10ReportVO;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;

/**
 * 报表
 */
@RestController
@RequestMapping("/admin/report")
@Slf4j
@Api(tags = "统计报表相关接口")
public class ReportController {

	@Autowired
	private ReportService reportService;

	/**
	 * 营业额数据统计
	 *
	 * @param begin 开始日期，格式为yyyy-MM-dd
	 * @param end   结束日期，格式为yyyy-MM-dd
	 * @return 包含营业额数据的结果对象
	 */
	@GetMapping("/turnoverStatistics")
	@ApiOperation("营业额数据统计")
	public Result<TurnoverReportVO> turnoverStatistics(
			@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
			@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {
		return Result.success(reportService.getTurnover(begin, end));
	}

	/**
	 * 用户数据统计
	 *
	 * @param begin 开始日期，格式为yyyy-MM-dd
	 * @param end   结束日期，格式为yyyy-MM-dd
	 * @return 包含用户数据的结果对象
	 */
	@GetMapping("/userStatistics")
	@ApiOperation("用户数据统计")
	public Result<UserReportVO> userStatistics(
			@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
			@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {

		return Result.success(reportService.getUserStatistics(begin, end));
	}

	/**
	 * 订单数据统计
	 *
	 * @param begin 开始日期，格式为yyyy-MM-dd
	 * @param end   结束日期，格式为yyyy-MM-dd
	 * @return 包含订单数据的结果对象
	 */
	@GetMapping("/ordersStatistics")
	@ApiOperation("订单数据统计")
	public Result<OrderReportVO> orderStatistics(
			@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
			@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {

		return Result.success(reportService.getOrderStatistics(begin, end));
	}

	/**
	 * 销量排名统计
	 *
	 * @param begin 开始日期，格式为yyyy-MM-dd
	 * @param end   结束日期，格式为yyyy-MM-dd
	 * @return 包含销量排名数据的结果对象
	 */
	@GetMapping("/top10")
	@ApiOperation("销量排名统计")
	public Result<SalesTop10ReportVO> top10(
			@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
			@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {
		return Result.success(reportService.getSalesTop10(begin, end));
	}

	/**
	 * 导出运营数据报表
	 *
	 * @param response HttpServletResponse对象，用于写入导出的报表数据
	 */
	@GetMapping("/export")
	@ApiOperation("导出运营数据报表")
	public void export(HttpServletResponse response) {
		reportService.exportBusinessData(response);
	}
}