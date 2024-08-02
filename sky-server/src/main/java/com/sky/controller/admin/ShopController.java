package com.sky.controller.admin;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController("adminShopController")
@RequestMapping("/admin/shop")
@Api(tags = "店铺相关接口")
@Slf4j
public class ShopController {

	public static final String KEY = "SHOP_STATUS";

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	/**
	 * 设置店铺的营业状态
	 *
	 * @param status 店铺的营业状态，1 表示营业中，其他值表示打烊中
	 * @return 返回操作结果的封装对象
	 */
	@PutMapping("/{status}")
	@ApiOperation("设置店铺的营业状态")
	public Result<Void> setStatus(@PathVariable Integer status) {
		// 记录日志，显示当前店铺的营业状态
		log.info("设置店铺的营业状态为：{}", status == 1 ? "营业中" : "打烊中");

		try {
			// 使用 RedisTemplate 将店铺状态存储到 Redis 中
			redisTemplate.opsForValue().set(KEY, status);
			log.info("店铺状态已成功存储到 Redis 中");
		} catch (Exception e) {
			log.error("无法将店铺状态存储到 Redis 中", e);
			return Result.error("无法将店铺状态存储到 Redis 中");
		}

		// 返回操作成功的结果
		return Result.success();
	}

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