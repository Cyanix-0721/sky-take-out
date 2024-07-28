package com.sky.service;

import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.result.PageResult;

public interface EmployeeService {

	/**
	 * 员工登录
	 *
	 * @param employeeLoginDTO 包含员工登录信息的数据传输对象
	 * @return 返回登录的员工实体
	 */
	Employee login(EmployeeLoginDTO employeeLoginDTO);

	/**
	 * 新增员工
	 *
	 * @param employeeDTO 包含员工信息的数据传输对象
	 */
	void save(EmployeeDTO employeeDTO);

	/**
	 * 启用禁用员工账号
	 * <p>
	 * 该方法用于启用或禁用员工账号。根据传入的状态值和员工ID，更新员工的状态。
	 *
	 * @param status 员工账号的状态。1表示启用，0表示禁用。
	 * @param id     员工的唯一标识符。
	 */
	void startOrStop(Integer status, Long id);

	/**
	 * 分页查询
	 * <p>
	 * 根据提供的分页查询参数，检索员工的分页列表。
	 *
	 * @param employeePageQueryDTO 包含分页查询参数的数据传输对象
	 * @return 包含员工分页列表的 PageResult 对象
	 */
	PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO);
}