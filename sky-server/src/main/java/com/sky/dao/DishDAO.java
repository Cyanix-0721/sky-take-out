package com.sky.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sky.entity.Dish;
import com.sky.mapper.DishMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DishDAO {

	@Autowired
	private DishMapper dishMapper;

	/**
	 * 根据分类id查询菜品
	 *
	 * @param dish 餐品对象
	 * @return 返回菜品列表
	 */
	public List<Dish> list(Dish dish) {
		return dishMapper.selectList(
				new LambdaQueryWrapper<Dish>()
						.like(dish.getName() != null, Dish::getName, dish.getName())
						.eq(dish.getCategoryId() != null, Dish::getCategoryId, dish.getCategoryId())
						.eq(dish.getStatus() != null, Dish::getStatus, dish.getStatus())
		);
	}
}
