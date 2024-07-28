package com.sky.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * PageResult 类用于封装分页查询的结果。
 * 它包含了分页查询所必需的两个关键信息：总记录数和当前页的数据集合。
 * 这个类可以方便地在服务端和客户端之间传递分页数据。
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResult implements Serializable {

	/**
	 * 总记录数，用于计算总页数和判断是否有下一页。
	 */
	private long total;

	/**
	 * 当前页的数据集合，包含了该页的所有记录。
	 */
	private List<?> records;

}
