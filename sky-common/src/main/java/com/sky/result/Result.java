package com.sky.result;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * 后端统一返回结果
 *
 * @param <T> 返回数据的类型
 */
@Data
@Builder
public class Result<T> implements Serializable {
	
	private final Integer code; // 编码：1成功，0和其它数字为失败
	private final String msg; // 错误信息
	private final T data; // 数据

	/**
	 * 成功时返回的结果
	 *
	 * @param <T> 返回数据的类型
	 * @return 成功的结果对象
	 */
	public static <T> Result<T> success() {
		return Result.<T>builder()
				.code(1)
				.build();
	}

	/**
	 * 成功时返回的结果
	 *
	 * @param object 返回的数据
	 * @param <T>    返回数据的类型
	 * @return 成功的结果对象
	 */
	public static <T> Result<T> success(T object) {
		return Result.<T>builder()
				.code(1)
				.data(object)
				.build();
	}

	/**
	 * 失败时返回的结果
	 *
	 * @param msg 错误信息
	 * @param <T> 返回数据的类型
	 * @return 失败的结果对象
	 */
	public static <T> Result<T> error(String msg) {
		return Result.<T>builder()
				.code(0)
				.msg(msg)
				.build();
	}
}