package com.sky.controller.admin;

import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.entity.Employee;
import com.sky.properties.JwtProperties;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import com.sky.utils.JwtUtil;
import com.sky.vo.EmployeeLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}