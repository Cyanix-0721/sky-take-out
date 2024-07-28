package com.sky.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface DishMapper {

	/**
	 * 根据分类id查询菜品数量
	 * <p>
	 * 该方法用于根据分类ID查询菜品的数量。
	 *
	 * @param categoryId 分类的唯一标识符
	 * @return 返回菜品数量
	 */
	@Select("select count(id) from dish where category_id = #{categoryId}")
	Integer countByCategoryId(Long categoryId);
}
