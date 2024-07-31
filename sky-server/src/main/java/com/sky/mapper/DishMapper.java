package com.sky.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sky.entity.Dish;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DishMapper extends BaseMapper<Dish> {
	/**
	 * 根据套餐id查询菜品
	 *
	 * @param setmealId 套餐ID
	 * @return 返回与指定套餐ID关联的菜品列表
	 */
	List<Dish> getBySetmealId(Long setmealId);
}