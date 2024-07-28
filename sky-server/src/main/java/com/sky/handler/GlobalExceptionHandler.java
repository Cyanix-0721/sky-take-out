package com.sky.handler;

import com.sky.constant.MessageConstant;
import com.sky.exception.BaseException;
import com.sky.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理器，处理项目中抛出的业务异常
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	/**
	 * 捕获业务异常
	 * 该方法在抛出BaseException时触发，
	 * 记录异常信息并返回包含错误消息的Result对象。
	 *
	 * @param ex 抛出的BaseException
	 * @return 包含错误消息的Result对象
	 */
	@ExceptionHandler
	public Result<Void> exceptionHandler(BaseException ex) {
		log.error("异常信息：{}", ex.getMessage());
		return Result.error(ex.getMessage());
	}

	/**
	 * 处理SQL异常
	 * 该方法在抛出SQLIntegrityConstraintViolationException时触发，
	 * 通常是由于违反数据库约束（如唯一键约束）导致的。
	 * 它检查异常消息以确定错误是否由于重复条目引起。
	 * 如果发现重复条目，它会提取冲突的用户名并返回一个错误消息，
	 * 指示用户名已存在。否则，它返回一个通用的未知错误消息。
	 *
	 * @param ex 抛出的SQLIntegrityConstraintViolationException
	 * @return 包含错误消息的Result对象
	 */
	@ExceptionHandler
	public Result<Void> exceptionHandler(SQLIntegrityConstraintViolationException ex) {
		// 提取异常消息
		String message = ex.getMessage();

		// 检查消息是否包含“Duplicate entry”
		if (message.contains("Duplicate entry")) {
			// 拆分消息以提取冲突的用户名
			String[] split = message.split(" ");
			String username = split[2];
			// 构建指示用户名已存在的错误消息
			String msg = username + MessageConstant.ALREADY_EXISTS;
			return Result.error(msg);
		} else {
			// 返回通用的未知错误消息
			return Result.error(MessageConstant.UNKNOWN_ERROR);
		}
	}
}
