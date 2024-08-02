package com.sky.service;

import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;

import java.util.List;

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

	/**
	 * 清空购物车商品
	 * <p>
	 * 该方法用于清空当前用户的购物车中的所有商品。
	 */
	void cleanShoppingCart();

	/**
	 * 删除购物车中一个商品
	 * <p>
	 * 该方法用于从购物车中删除一个商品。
	 *
	 * @param shoppingCartDTO 购物车数据传输对象，包含要删除的商品信息
	 */
	void subShoppingCart(ShoppingCartDTO shoppingCartDTO);

	/**
	 * 查看购物车
	 * <p>
	 * 该方法用于查看当前用户的购物车中的所有商品。
	 *
	 * @return 返回购物车中的商品列表
	 */
	List<ShoppingCart> showShoppingCart();
}