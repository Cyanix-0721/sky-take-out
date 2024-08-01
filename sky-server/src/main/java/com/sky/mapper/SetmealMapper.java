package com.sky.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sky.entity.Setmeal;
import com.sky.vo.DishItemVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SetmealMapper extends BaseMapper<Setmeal> {
	/**
	 * 根据套餐id查询菜品选项
	 * <p>
	 * 该方法用于根据套餐id查询菜品选项。它接收一个套餐id，并返回一个包含符合条件的菜品选项列表。
	 *
	 * @param setmealId 套餐的id
	 * @return 返回一个包含符合条件的菜品List\<DishItemVO\>对象
	 */
	List<DishItemVO> getDishItemBySetmealId(Long setmealId);
}