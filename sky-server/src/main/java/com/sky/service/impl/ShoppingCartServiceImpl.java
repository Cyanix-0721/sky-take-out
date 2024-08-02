package com.sky.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.ShoppingCart;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.mapper.ShoppingCartMapper;
import com.sky.service.ShoppingCartService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

	@Autowired
	private ShoppingCartMapper shoppingCartMapper;
	@Autowired
	private DishMapper dishMapper;
	@Autowired
	private SetmealMapper setmealMapper;

	/**
	 * 添加购物车
	 * <p>
	 * 该方法检查商品（菜品或套餐）是否已经在购物车中。
	 * 如果商品已经存在，则将数量加1。
	 * 如果商品不存在，则将商品添加到购物车中，数量为1。
	 *
	 * @param shoppingCartDTO 购物车数据传输对象，包含要添加的商品信息
	 */
	public void addShoppingCart(ShoppingCartDTO shoppingCartDTO) {
		ShoppingCart shoppingCart = new ShoppingCart();
		BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);
		//只能查询自己的购物车数据
		shoppingCart.setUserId(BaseContext.getCurrentId());

		//判断当前商品是否在购物车中
		LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(shoppingCart.getUserId() != null, ShoppingCart::getUserId, shoppingCart.getUserId())
				.eq(shoppingCart.getDishId() != null, ShoppingCart::getDishId, shoppingCart.getDishId())
				.eq(shoppingCart.getSetmealId() != null, ShoppingCart::getSetmealId, shoppingCart.getSetmealId())
				.eq(shoppingCart.getDishFlavor() != null, ShoppingCart::getDishFlavor, shoppingCart.getDishFlavor())
				.orderByDesc(ShoppingCart::getCreateTime);

		// 查询购物车数据
		List<ShoppingCart> shoppingCartList = shoppingCartMapper.selectList(queryWrapper);

		if (shoppingCartList != null && shoppingCartList.size() == 1) {
			//如果已经存在，就更新数量，数量加1
			shoppingCart = shoppingCartList.get(0);
			shoppingCart.setNumber(shoppingCart.getNumber() + 1);
			shoppingCartMapper.updateById(shoppingCart);
		} else {
			//如果不存在，插入数据，数量就是1

			//判断当前添加到购物车的是菜品还是套餐
			Long dishId = shoppingCartDTO.getDishId();
			if (dishId != null) {
				//添加到购物车的是菜品
				Dish dish = dishMapper.selectById(dishId);
				shoppingCart.setName(dish.getName());
				shoppingCart.setImage(dish.getImage());
				shoppingCart.setAmount(dish.getPrice());
			} else {
				//添加到购物车的是套餐
				Setmeal setmeal = setmealMapper.selectById(shoppingCartDTO.getSetmealId());
				shoppingCart.setName(setmeal.getName());
				shoppingCart.setImage(setmeal.getImage());
				shoppingCart.setAmount(setmeal.getPrice());
			}
			shoppingCart.setNumber(1);
			shoppingCartMapper.insert(shoppingCart);
		}
	}
}

