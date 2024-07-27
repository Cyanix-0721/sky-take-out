package com.sky.service.impl;

import com.sky.constant.MessageConstant;
import com.sky.constant.PasswordConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.entity.Employee;
import com.sky.exception.AccountLockedException;
import com.sky.exception.AccountNotFoundException;
import com.sky.exception.PasswordErrorException;
import com.sky.mapper.EmployeeMapper;
import com.sky.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;

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
		String username = employeeLoginDTO.getUsername();
		String password = employeeLoginDTO.getPassword();

		// 1、根据用户名查询数据库中的数据
		Employee employee = employeeMapper.getByUsername(username);

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

		// 设置密码，默认密码123456
		employee.setPassword(DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes()));

		// 设置当前记录的创建时间和修改时间
		employee.setCreateTime(LocalDateTime.now());
		employee.setUpdateTime(LocalDateTime.now());

		// 设置当前记录创建人id和修改人id
		employee.setCreateUser(10L); // 目前写个假数据，后期修改
		employee.setUpdateUser(10L);

		// 插入员工记录到数据库
		employeeMapper.insert(employee); // 后续步骤定义
	}
}