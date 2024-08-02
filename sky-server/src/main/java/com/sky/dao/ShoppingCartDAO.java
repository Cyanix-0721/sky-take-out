package com.sky.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sky.entity.Dish;
import com.sky.entity.ShoppingCart;
import com.sky.mapper.DishMapper;
import com.sky.mapper.ShoppingCartMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ShoppingCartDAO {
	@Autowired
	private ShoppingCartMapper shoppingCartMapper;

	/**
	 * 根据用户ID查询购物车
	 *
	 * @param shoppingCart 购物车对象，包含查询条件
	 * @return 返回购物车列表
	 */
	public List<ShoppingCart> list(ShoppingCart shoppingCart) {
		return shoppingCartMapper.selectList(new LambdaQueryWrapper<>(shoppingCart)
				.eq(shoppingCart.getUserId() != null, ShoppingCart::getUserId, shoppingCart.getUserId())
				.eq(shoppingCart.getDishId() != null, ShoppingCart::getDishId, shoppingCart.getDishId())
				.eq(shoppingCart.getSetmealId() != null, ShoppingCart::getSetmealId, shoppingCart.getSetmealId())
				.eq(shoppingCart.getDishFlavor() != null, ShoppingCart::getDishFlavor, shoppingCart.getDishFlavor())
				.orderByDesc(ShoppingCart::getCreateTime));
	}
}
