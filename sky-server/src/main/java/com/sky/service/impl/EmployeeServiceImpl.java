package com.sky.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sky.constant.MessageConstant;
import com.sky.constant.PasswordConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.exception.AccountLockedException;
import com.sky.exception.AccountNotFoundException;
import com.sky.exception.PasswordErrorException;
import com.sky.mapper.EmployeeMapper;
import com.sky.result.PageResult;
import com.sky.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeMapper employeeMapper;

	/**
	 * 员工登录
	 *
	 * @param employeeLoginDTO 包含员工登录信息的数据传输对象
	 * @return 登录成功的员工实体对象
	 * @throws AccountNotFoundException 如果账号不存在
	 * @throws PasswordErrorException   如果密码错误
	 * @throws AccountLockedException   如果账号被锁定
	 */
	public Employee login(EmployeeLoginDTO employeeLoginDTO) {
		// 获取用户名和密码
		String username = employeeLoginDTO.getUsername();
		String password = employeeLoginDTO.getPassword();

		// 1、根据用户名查询数据库中的数据
		Employee employee = employeeMapper.selectOne(new QueryWrapper<Employee>().eq("username", username));

		// 2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
		if (employee == null) {
			// 账号不存在
			throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
		}

		// 密码比对
		password = DigestUtils.md5DigestAsHex(password.getBytes());
		if (!password.equals(employee.getPassword())) {
			// 密码错误
			throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
		}

		if (StatusConstant.DISABLE.equals(employee.getStatus())) {
			// 账号被锁定
			throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
		}

		// 3、返回实体对象
		return employee;
	}

	/**
	 * 新增员工
	 *
	 * @param employeeDTO 包含员工信息的数据传输对象
	 */
	public void save(EmployeeDTO employeeDTO) {
		Employee employee = new Employee();

		// 对象属性拷贝
		BeanUtils.copyProperties(employeeDTO, employee);

		// 设置账号的状态，默认正常状态 1表示正常 0表示锁定
		employee.setStatus(StatusConstant.ENABLE);

		// 设置密码，默认密码 123456 MD5加密=> e10adc3949ba59abbe56e057f20f883e
		employee.setPassword(DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes()));

		// 设置当前记录的创建时间/修改时间/创建人id/修改人id
//		employee.setCreateTime(LocalDateTime.now());
//		employee.setUpdateTime(LocalDateTime.now());
//		employee.setCreateUser(BaseContext.getCurrentId());
//		employee.setUpdateUser(BaseContext.getCurrentId());

		// 插入员工记录到数据库
		employeeMapper.insert(employee);
	}

	/**
	 * 启用禁用员工账号
	 * <p>
	 * 该方法用于启用或禁用员工账号。根据传入的状态值和员工ID，更新员工的状态。
	 *
	 * @param status 员工账号的状态。1表示启用，0表示禁用。
	 * @param id     员工的唯一标识符。
	 */
	public void startOrStop(Integer status, Long id) {
		// 创建一个新的Employee对象，并设置其状态和ID
		Employee employee = Employee.builder()
				.id(id)
				.status(status)
//				.updateTime(LocalDateTime.now())
//				.updateUser(BaseContext.getCurrentId())
				.build();

		employeeMapper.updateById(employee);
	}

	/**
	 * 编辑员工信息
	 *
	 * @param employeeDTO 包含员工信息的数据传输对象
	 */
	public void update(EmployeeDTO employeeDTO) {
		Employee employee = new Employee();
		BeanUtils.copyProperties(employeeDTO, employee);

//		employee.setUpdateTime(LocalDateTime.now());
//		employee.setUpdateUser(BaseContext.getCurrentId());

		employeeMapper.updateById(employee);
	}


	/**
	 * 分页查询
	 * <p>
	 * 根据提供的分页查询参数，检索员工的分页列表。
	 *
	 * @param employeePageQueryDTO 包含分页查询参数的数据传输对象
	 * @return 包含员工分页列表的 PageResult 对象
	 */
	public PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO) {
		// 创建分页对象
		Page<Employee> page = new Page<>(employeePageQueryDTO.getPage(), employeePageQueryDTO.getPageSize());

		// 执行分页查询
		Page<Employee> resultPage = employeeMapper.selectPage(page, new QueryWrapper<Employee>()
				.like(employeePageQueryDTO.getName() != null && !employeePageQueryDTO.getName().isEmpty(), "name", employeePageQueryDTO.getName())
				.orderByDesc("create_time"));

		// 获取总记录数和员工列表
		long total = resultPage.getTotal();
		List<Employee> records = resultPage.getRecords();

		// 返回分页结果
		return new PageResult(total, records);
	}

	/**
	 * 根据id查询员工
	 * <p>
	 * 该方法用于根据员工的唯一标识符查询员工的详细信息。
	 * 它从数据库中检索员工信息，并将密码字段设置为"****"以隐藏实际密码。
	 *
	 * @param id 员工的唯一标识符
	 * @return 返回包含员工详细信息的 Employee 对象
	 */
	public Employee getById(Long id) {
		Employee employee = employeeMapper.selectById(id);
		employee.setPassword("****");
		return employee;
	}
}