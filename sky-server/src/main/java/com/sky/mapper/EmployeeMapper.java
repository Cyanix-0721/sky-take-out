package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

/**
 * 员工数据访问对象（DAO）接口
 * 提供对员工数据的访问方法
 */
@Mapper
public interface EmployeeMapper {

	/**
	 * 根据用户名查询员工
	 *
	 * @param username 员工的用户名
	 * @return 员工实体对象
	 */
	Employee getByUsername(String username);

	/**
	 * 插入员工数据
	 *
	 * @param employee 员工对象
	 */
	void insert(Employee employee);

	/**
	 * 根据主键动态修改属性
	 *
	 * @param employee 员工对象
	 */
	void update(Employee employee);

	/**
	 * 分页查询
	 * <p>
	 * 根据提供的分页查询参数，检索员工的分页列表。
	 *
	 * @param employeePageQueryDTO 包含分页查询参数的数据传输对象
	 * @return 包含员工分页列表的 Page 对象
	 */
	Page<Employee> pageQuery(EmployeePageQueryDTO employeePageQueryDTO);
}