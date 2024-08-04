package com.sky.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sky.entity.Dish;
import com.sky.mapper.DishMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

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

	/**
	 * 根据条件统计菜品数量
	 *
	 * @param map 包含查询参数的映射
	 * @return 菜品数量
	 */
	public Integer countByMap(Map<String, Object> map) {
		LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();

		queryWrapper
				.eq(map.get("status") != null, Dish::getStatus, map.get("status"))
				.eq(map.get("categoryId") != null, Dish::getCategoryId, map.get("categoryId"));

		return Math.toIntExact(dishMapper.selectCount(queryWrapper));
	}
}
