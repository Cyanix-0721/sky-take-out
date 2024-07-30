package com.sky.service;

import com.sky.dto.DishDTO;

public interface DishService {

	/**
	 * 新增菜品和对应的口味
	 * <p>
	 * 该方法用于将一个菜品及其对应的口味信息保存到数据库中。
	 * 它首先将菜品信息保存到菜品表中，然后获取生成的主键值，
	 * 并将该主键值设置到每个口味对象中，最后将这些口味对象保存到口味表中。
	 *
	 * @param dishDTO 包含菜品和口味信息的数据传输对象
	 */
	public void saveWithFlavor(DishDTO dishDTO);
}