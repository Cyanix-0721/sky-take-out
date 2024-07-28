package com.sky.controller.admin;

import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.properties.JwtProperties;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import com.sky.utils.JwtUtil;
import com.sky.vo.EmployeeLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理控制器
 */
@RestController
@RequestMapping("/admin/employee")
@Slf4j
@Api(tags = "员工管理")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private JwtProperties jwtProperties;

	/**
	 * 员工登录
	 *
	 * @param employeeLoginDTO 包含员工登录信息的数据传输对象
	 * @return 包含登录信息的结果对象
	 */
	@PostMapping("/login")
	@ApiOperation("员工登录")
	public Result<EmployeeLoginVO> login(@ApiParam("员工登录信息") @RequestBody EmployeeLoginDTO employeeLoginDTO) {
		log.info("员工登录：{}", employeeLoginDTO);

		Employee employee = employeeService.login(employeeLoginDTO);

		// 登录成功后，生成jwt令牌
		Map<String, Object> claims = new HashMap<>();
		claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
		String token = JwtUtil.createJWT(
				jwtProperties.getAdminSecretKey(),
				jwtProperties.getAdminTtl(),
				claims);

		EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
				.id(employee.getId())
				.userName(employee.getUsername())
				.name(employee.getName())
				.token(token)
				.build();

		return Result.success(employeeLoginVO);
	}

	/**
	 * 员工退出
	 *
	 * @return 成功退出的结果对象
	 */
	@PostMapping("/logout")
	@ApiOperation("员工退出")
	public Result<String> logout() {
		return Result.success();
	}

	/**
	 * 新增员工
	 *
	 * @param employeeDTO 包含员工信息的数据传输对象
	 * @return 成功新增员工的结果对象
	 */
	@PostMapping
	@ApiOperation("新增员工")
	public Result<Void> save(@ApiParam("员工信息") @RequestBody EmployeeDTO employeeDTO) {
		log.info("新增员工：{}", employeeDTO);
		employeeService.save(employeeDTO);
		return Result.success();
	}

	/**
	 * 启用禁用员工账号
	 *
	 * @param status 员工账号的状态。1表示启用，0表示禁用。
	 * @param id     员工的唯一标识符。
	 * @return 成功启用禁用员工账号的结果对象
	 */
	@PostMapping("/status/{status}")
	@ApiOperation("启用禁用员工账号")
	public Result<Void> startOrStop(@PathVariable Integer status, Long id) {
		log.info("启用禁用员工账号：{},{}", status, id);
		employeeService.startOrStop(status, id);
		return Result.success();
	}

	/**
	 * 编辑员工信息
	 * <p>
	 * 该方法用于编辑员工的详细信息。
	 * 它映射到 PUT HTTP 方法。
	 *
	 * @param employeeDTO 包含员工信息的数据传输对象
	 * @return 成功编辑员工信息的结果对象
	 */
	@PutMapping
	@ApiOperation("编辑员工信息")
	public Result<Void> update(@RequestBody EmployeeDTO employeeDTO) {
		log.info("编辑员工信息：{}", employeeDTO);
		employeeService.update(employeeDTO);
		return Result.success();
	}

	/**
	 * 员工分页查询
	 * <p>
	 * 该方法映射到 GET HTTP 方法和 "/page" URL。
	 * 它根据提供的查询参数检索员工的分页列表。
	 * </p>
	 *
	 * @param employeePageQueryDTO 包含分页查询参数的数据传输对象
	 * @return 包含员工分页列表的 PageResult 对象的 Result
	 */
	@GetMapping("/page")
	@ApiOperation("员工分页查询")
	public Result<PageResult> page(EmployeePageQueryDTO employeePageQueryDTO) {
		log.info("员工分页查询，参数为：{}", employeePageQueryDTO);
		PageResult pageResult = employeeService.pageQuery(employeePageQueryDTO);
		return Result.success(pageResult);
	}

	/**
	 * 根据id查询员工信息
	 * <p>
	 * 该方法用于根据员工的唯一标识符查询员工的详细信息。
	 * 它映射到 GET HTTP 方法和 "/{id}" URL。
	 *
	 * @param id 员工的唯一标识符
	 * @return 包含员工详细信息的 Result 对象
	 */
	@GetMapping("/{id}")
	@ApiOperation("根据id查询员工信息")
	public Result<Employee> getById(@PathVariable Long id) {
		Employee employee = employeeService.getById(id);
		return Result.success(employee);
	}
}