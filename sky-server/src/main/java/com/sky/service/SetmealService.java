package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.result.PageResult;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;

import java.util.List;

public interface SetmealService {

	/**
	 * 新增套餐，同时需要保存套餐和菜品的关联关系
	 * <p>
	 * 该方法接收一个包含套餐信息的SetmealDTO对象，并将套餐信息和菜品的关联关系保存到数据库中。
	 *
	 * @param setmealDTO 包含套餐信息的DTO对象
	 */
	void saveWithDish(SetmealDTO setmealDTO);

	/**
	 * 批量删除套餐
	 * <p>
	 * 该方法用于批量删除套餐。它接收一个包含要删除的套餐ID列表，
	 * 并调用SetmealService的deleteBatch方法删除这些套餐。
	 *
	 * @param ids 包含要删除的套餐ID列表
	 */
	void deleteBatch(List<Long> ids);

	/**
	 * 修改套餐
	 * <p>
	 * 该方法用于修改套餐信息。它接收一个包含套餐信息的SetmealDTO对象，
	 * 并使用SetmealService的update方法更新套餐信息。
	 *
	 * @param setmealDTO 包含套餐信息的DTO对象
	 */
	void update(SetmealDTO setmealDTO);

	/**
	 * 套餐起售、停售
	 * <p>
	 * 该方法用于修改套餐的销售状态。它接收一个状态值和一个套餐ID，
	 * 并调用SetmealService的startOrStop方法更新套餐的销售状态。
	 *
	 * @param status 套餐的销售状态，1表示起售，0表示停售
	 * @param id     套餐的ID
	 */
	void startOrStop(Integer status, Long id);

	/**
	 * 分页查询
	 * <p>
	 * 该方法用于分页查询套餐信息。它接收一个包含查询条件的SetmealPageQueryDTO对象，
	 * 并调用SetmealService的pageQuery方法获取分页结果。
	 *
	 * @param setmealPageQueryDTO 包含查询条件的DTO对象
	 * @return 返回一个PageResult对象，包含分页查询的结果
	 */
	PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

	/**
	 * 条件查询
	 * <p>
	 * 该方法用于根据给定的条件查询套餐信息。它接收一个包含查询条件的Setmeal对象，
	 * 并返回一个包含符合条件的套餐列表。
	 *
	 * @param setmeal 包含查询条件的Setmeal对象
	 * @return 返回一个包含符合条件的套餐List\<Setmeal\>对象
	 */
	List<Setmeal> list(Setmeal setmeal);

	/**
	 * 根据id查询套餐和关联的菜品数据
	 * <p>
	 * 该方法用于根据套餐ID查询套餐信息及其关联的菜品数据。
	 *
	 * @param id 套餐的ID
	 * @return 返回一个包含套餐信息和关联菜品数据的SetmealVO对象
	 */
	SetmealVO getByIdWithDish(Long id);

	/**
	 * 根据id查询菜品选项
	 * <p>
	 * 该方法用于根据菜品ID查询菜品选项。它接收一个菜品ID，并返回一个包含菜品选项的列表。
	 *
	 * @param id 菜品的ID
	 * @return 返回一个包含菜品选项的List\<DishItemVO\>对象
	 */
	List<DishItemVO> getDishItemById(Long id);
}