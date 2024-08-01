package com.sky.controller.user;

import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.properties.JwtProperties;
import com.sky.result.Result;
import com.sky.service.UserService;
import com.sky.utils.JwtUtil;
import com.sky.vo.UserLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user/user")
@Api(tags = "C端用户相关接口")
@Slf4j
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private JwtProperties jwtProperties;

	/**
	 * 微信登录
	 * <p>
	 * 该方法映射到 "/login" 端点的 POST 请求。
	 * 它接受一个 `UserLoginDTO` 对象作为请求体，其中包含微信用户的必要登录信息。
	 * <p>
	 * 该方法执行以下步骤：
	 * 1. 使用提供的微信代码记录登录尝试。
	 * 2. 调用 `UserService` 的 `wxLogin` 方法来验证用户。
	 * 3. 为验证的用户生成 JWT 令牌。
	 * 4. 构建一个包含用户 ID、OpenID 和生成的令牌的 `UserLoginVO` 对象。
	 * 5. 返回包含 `UserLoginVO` 对象的成功结果。
	 *
	 * @param userLoginDTO 包含微信登录信息的数据传输对象
	 * @return 包含用户详细信息和 JWT 令牌的 `Result` 对象
	 */
	@PostMapping("/login")
	@ApiOperation("微信登录")
	public Result<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO) {
		log.info("微信用户登录：{}", userLoginDTO.getCode());

		// 微信登录
		User user = userService.wxLogin(userLoginDTO);

		// 为微信用户生成 jwt 令牌
		Map<String, Object> claims = new HashMap<>();
		claims.put(JwtClaimsConstant.USER_ID, user.getId());
		String token = JwtUtil.createJWT(jwtProperties.getUserSecretKey(), jwtProperties.getUserTtl(), claims);

		// 构建 UserLoginVO 对象
		UserLoginVO userLoginVO = UserLoginVO.builder()
				.id(user.getId())
				.openid(user.getOpenid())
				.token(token)
				.build();

		// 返回包含 UserLoginVO 对象的成功结果
		return Result.success(userLoginVO);
	}
}