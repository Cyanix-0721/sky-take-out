package com.sky.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * EmployeePageQueryDTO 是一个用于分页查询员工信息的数据传输对象（DTO）。
 * 它包含了查询员工列表时所需的基本参数，如员工姓名、页码和每页显示的记录数。
 * </p>
 */
@Data
@ApiModel(value = "EmployeePageQueryDTO", description = "员工分页查询参数")
public class EmployeePageQueryDTO implements Serializable {

	/**
	 * 员工姓名，用于模糊搜索。
	 */
	@ApiModelProperty(value = "员工姓名", example = "张三")
	private String name;

	/**
	 * 页码，从1开始计数。
	 */
	@ApiModelProperty(value = "页码", example = "1")
	private int page;

	/**
	 * 每页显示的记录数。
	 */
	@ApiModelProperty(value = "每页显示记录数", example = "10")
	private int pageSize;

}
