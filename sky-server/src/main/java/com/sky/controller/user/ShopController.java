package com.sky.controller.user;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController("userShopController")
@RequestMapping("/user/shop")
@Api(tags = "店铺相关接口")
@Slf4j
public class ShopController {

	public static final String KEY = "SHOP_STATUS";

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	/**
	 * 获取店铺的营业状态
	 *
	 * @return 返回操作结果的封装对象，包含店铺的营业状态
	 */
	@GetMapping("/status")
	@ApiOperation("获取店铺的营业状态")
	public Result<Integer> getStatus() {
		// 从 Redis 中获取店铺的营业状态
		Integer status = (Integer) redisTemplate.opsForValue().get(KEY);

		// 记录日志，显示当前店铺的营业状态
		log.info("获取到店铺的营业状态为：{}", status == 1 ? "营业中" : "打烊中");

		// 返回操作成功的结果，包含店铺的营业状态
		return Result.success(status);
	}
}