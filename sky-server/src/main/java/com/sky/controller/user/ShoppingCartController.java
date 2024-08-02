package com.sky.controller.user;


import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import com.sky.result.Result;
import com.sky.service.ShoppingCartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 购物车
 */
@RestController
@RequestMapping("/user/shoppingCart")
@Slf4j
@Api(tags = "C端-购物车接口")
public class ShoppingCartController {

	@Autowired
	private ShoppingCartService shoppingCartService;

	/**
	 * 添加购物车
	 * <p>
	 * 该方法用于将商品添加到购物车中。
	 *
	 * @param shoppingCartDTO 购物车数据传输对象，包含要添加的商品信息
	 * @return 返回操作结果的字符串
	 */
	@PostMapping("/add")
	@ApiOperation("添加购物车")
	public Result<Void> add(@RequestBody ShoppingCartDTO shoppingCartDTO) {
		log.info("添加购物车：{}", shoppingCartDTO);
		shoppingCartService.addShoppingCart(shoppingCartDTO);
		return Result.success();
	}

	/**
	 * 清空购物车商品
	 * <p>
	 * 该方法用于清空当前用户的购物车中的所有商品。
	 *
	 * @return 返回操作结果的字符串
	 */
	@DeleteMapping("/clean")
	@ApiOperation("清空购物车")
	public Result<Void> clean() {
		log.info("清空购物车");
		shoppingCartService.cleanShoppingCart();
		return Result.success();
	}

	/**
	 * 删除购物车中一个商品
	 * <p>
	 * 该方法用于从购物车中删除一个商品。
	 *
	 * @param shoppingCartDTO 购物车数据传输对象，包含要删除的商品信息
	 * @return 返回操作结果的封装对象
	 */
	@PostMapping("/sub")
	@ApiOperation("删除购物车中一个商品")
	public Result<Void> sub(@RequestBody ShoppingCartDTO shoppingCartDTO) {
		log.info("删除购物车中一个商品，商品：{}", shoppingCartDTO);
		shoppingCartService.subShoppingCart(shoppingCartDTO);
		return Result.success();
	}

	/**
	 * 查看购物车
	 * <p>
	 * 该方法用于获取当前用户的购物车列表。
	 *
	 * @return 返回包含购物车中所有商品的列表的封装对象
	 */
	@GetMapping("/list")
	@ApiOperation("查看购物车")
	public Result<List<ShoppingCart>> list() {
		log.info("查看购物车");
		return Result.success(shoppingCartService.showShoppingCart());
	}
}
