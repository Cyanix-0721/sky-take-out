package com.sky.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sky.context.BaseContext;
import com.sky.dao.ShoppingCartDAO;
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
	private ShoppingCartDAO shoppingCartDAO;
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
	@Override
	public void addShoppingCart(ShoppingCartDTO shoppingCartDTO) {
		ShoppingCart shoppingCart = new ShoppingCart();
		BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);
		//只能查询自己的购物车数据
		shoppingCart.setUserId(BaseContext.getCurrentId());

		//判断当前商品是否在购物车中
		List<ShoppingCart> shoppingCartList = shoppingCartDAO.list(shoppingCart);

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

	@Override
	public void cleanShoppingCart() {
		shoppingCartMapper.delete(new LambdaQueryWrapper<ShoppingCart>()
				.eq(ShoppingCart::getUserId, BaseContext.getCurrentId()));
	}

	/**
	 * 删除购物车中一个商品
	 * <p>
	 * 该方法用于从购物车中删除一个商品。
	 * 如果商品在购物车中的数量为1，则直接删除该商品。
	 * 如果商品在购物车中的数量大于1，则将数量减1。
	 *
	 * @param shoppingCartDTO 购物车数据传输对象，包含要删除的商品信息
	 */
	@Override
	public void subShoppingCart(ShoppingCartDTO shoppingCartDTO) {
		ShoppingCart shoppingCart = new ShoppingCart();
		BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);
		// 设置查询条件，查询当前登录用户的购物车数据
		shoppingCart.setUserId(BaseContext.getCurrentId());
		// 查询当前商品在购物车中的记录
		shoppingCartDAO.list(shoppingCart).stream().findFirst().ifPresent(cart -> {
			Integer number = cart.getNumber();
			if (number == 1) {
				// 当前商品在购物车中的份数为1，直接删除当前记录
				shoppingCartMapper.deleteById(cart.getId());
			} else {
				// 当前商品在购物车中的份数不为1，修改份数即可
				cart.setNumber(cart.getNumber() - 1);
				shoppingCartMapper.updateById(cart);
			}
		});
	}

	/**
	 * 查看购物车
	 * <p>
	 * 该方法用于查看当前用户的购物车中的所有商品。
	 *
	 * @return 返回购物车中的商品列表
	 */
	@Override
	public List<ShoppingCart> showShoppingCart() {
		return shoppingCartDAO.list(ShoppingCart.builder()
				.userId(BaseContext.getCurrentId())
				.build());
	}
}

