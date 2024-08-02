package com.sky.service;

import com.sky.dto.ShoppingCartDTO;

/**
 * 购物车服务接口
 * <p>
 * 该接口定义了购物车服务的操作方法。
 */
public interface ShoppingCartService {

	/**
	 * 添加购物车
	 * <p>
	 * 该方法用于将商品添加到购物车中。
	 *
	 * @param shoppingCartDTO 购物车数据传输对象，包含要添加的商品信息
	 */
	void addShoppingCart(ShoppingCartDTO shoppingCartDTO);
}