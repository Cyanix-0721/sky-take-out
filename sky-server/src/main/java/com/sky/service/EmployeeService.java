package com.sky.service;

import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.entity.Employee;

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
}