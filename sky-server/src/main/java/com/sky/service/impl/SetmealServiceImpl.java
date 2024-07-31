package com.sky.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.exception.SetmealEnableFailedException;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 套餐业务实现
 */
@Service
@Slf4j
public class SetmealServiceImpl implements SetmealService {

	@Autowired
	private SetmealMapper setmealMapper;
	@Autowired
	private SetmealDishMapper setmealDishMapper;
	@Autowired
	private DishMapper dishMapper;

	/**
	 * 新增套餐，同时需要保存套餐和菜品的关联关系
	 * <p>
	 * 该方法用于将套餐及其关联的菜品信息保存到数据库中。
	 * 它首先将套餐信息保存到套餐表中，然后获取生成的主键值，
	 * 并将该主键值设置到每个关联的菜品对象中，最后将这些菜品对象保存到套餐菜品关联表中。
	 *
	 * @param setmealDTO 包含套餐和菜品信息的数据传输对象
	 */
	@Override
	@Transactional
	public void saveWithDish(SetmealDTO setmealDTO) {

		Setmeal setmeal = new Setmeal();
		BeanUtils.copyProperties(setmealDTO, setmeal);

		setmealMapper.insert(setmeal);

		Optional.ofNullable(setmealDTO.getSetmealDishes())
				.filter(setmealDishes -> ! setmealDishes.isEmpty())
				.ifPresent(setmealDishes -> {
					setmealDishes.forEach(dish -> dish.setSetmealId(setmeal.getId()));
					setmealDishMapper.insert(setmealDishes);
				});
	}

	/**
	 * 批量删除套餐
	 * <p>
	 * 该方法用于批量删除套餐。它接收一个包含要删除的套餐ID列表，
	 * 并调用SetmealService的deleteBatch方法删除这些套餐。
	 *
	 * @param ids 包含要删除的套餐ID列表
	 */
	@Override
	@Transactional
	public void deleteBatch(List<Long> ids) {
		// 查询所有要删除的套餐
		List<Setmeal> setmeals = setmealMapper.selectBatchIds(ids);

		// 检查是否有起售中的套餐
		setmeals.forEach(setmeal -> {
			if (StatusConstant.ENABLE.equals(setmeal.getStatus())) {
				// 起售中的套餐不能删除
				throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ON_SALE);
			}
		});

		// 删除套餐表中的数据
		setmealMapper.deleteByIds(ids);

		// 删除套餐菜品关系表中的数据
		ids.forEach(setmealId ->
				setmealDishMapper.delete(new LambdaQueryWrapper<SetmealDish>()
						.eq(SetmealDish::getSetmealId, setmealId))
		);
	}

	/**
	 * 修改套餐
	 *
	 * @param setmealDTO 包含套餐和菜品信息的数据传输对象
	 */
	@Override
	@Transactional
	public void update(SetmealDTO setmealDTO) {
		// 创建一个新的Setmeal对象
		Setmeal setmeal = new Setmeal();

		// 将setmealDTO的属性复制到setmeal对象中
		BeanUtils.copyProperties(setmealDTO, setmeal);

		// 更新套餐信息到数据库
		setmealMapper.updateById(setmeal);

		// 获取套餐ID
		Long setmealId = setmealDTO.getId();

		// 删除旧的套餐菜品关联关系
		setmealDishMapper.delete(new LambdaQueryWrapper<SetmealDish>().eq(SetmealDish::getSetmealId, setmealId));

		// 如果setmealDTO中的setmealDishes不为空，则更新套餐菜品关联关系
		Optional.ofNullable(setmealDTO.getSetmealDishes())
				.filter(setmealDishes -> ! setmealDishes.isEmpty())
				.ifPresent(setmealDishes -> {
					// 设置每个菜品的套餐ID
					setmealDishes.forEach(setmealDish -> setmealDish.setSetmealId(setmealId));
					// 插入新的套餐菜品关联关系到数据库
					setmealDishMapper.insert(setmealDishes);
				});
	}

	/**
	 * 套餐起售、停售
	 *
	 * @param status 套餐状态，1表示起售，0表示停售
	 * @param id     套餐ID
	 */
	@Override
	public void startOrStop(Integer status, Long id) {
		if (StatusConstant.ENABLE.equals(status)) {
			// 查询套餐内的菜品是否都已启售
			//select a.* from dish a left join setmeal_dish b on a.id = b.dish_id where b.setmeal_id = ?
			List<Dish> dishes = dishMapper.getBySetmealId(id);

			Optional.ofNullable(dishes)
					.orElse(Collections.emptyList())
					.stream()
					.filter(dish -> StatusConstant.DISABLE.equals(dish.getStatus()))
					.findAny()
					.ifPresent(dish -> {
						// 套餐内包含未启售菜品，无法启售
						throw new SetmealEnableFailedException(MessageConstant.SETMEAL_ENABLE_FAILED);
					});
		}

		// 更新套餐状态
		Setmeal setmeal = Setmeal.builder()
				.id(id)
				.status(status)
				.build();

		setmealMapper.updateById(setmeal);
	}

	/**
	 * 分页查询
	 * <p>
	 * 该方法用于分页查询套餐信息。它接收一个包含查询条件的SetmealPageQueryDTO对象，
	 * 并调用SetmealService的pageQuery方法获取分页结果。
	 *
	 * @param setmealPageQueryDTO 包含查询条件的DTO对象
	 * @return 返回一个PageResult对象，包含分页查询的结果
	 */
	@Override
	public PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO) {
		// 创建分页对象
		Page<Setmeal> page = new Page<>(setmealPageQueryDTO.getPage(), setmealPageQueryDTO.getPageSize());

		// 构建查询条件
		LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.like(setmealPageQueryDTO.getName() != null && ! setmealPageQueryDTO.getName().isEmpty(), Setmeal::getName, setmealPageQueryDTO.getName())
				.eq(setmealPageQueryDTO.getCategoryId() != null, Setmeal::getCategoryId, setmealPageQueryDTO.getCategoryId())
				.eq(setmealPageQueryDTO.getStatus() != null, Setmeal::getStatus, setmealPageQueryDTO.getStatus())
				.orderByDesc(Setmeal::getCreateTime);

		// 执行查询
		Page<Setmeal> resultPage = setmealMapper.selectPage(page, queryWrapper);

		// 将结果转换为SetmealVO列表
		List<SetmealVO> setmealVOList = resultPage.getRecords().stream().map(setmeal -> {
			SetmealVO setmealVO = new SetmealVO();
			BeanUtils.copyProperties(setmeal, setmealVO);
			return setmealVO;
		}).collect(Collectors.toList());

		// 返回分页结果
		return new PageResult(resultPage.getTotal(), setmealVOList);
	}

	/**
	 * 根据id查询套餐和套餐菜品关系
	 * <p>
	 * 该方法用于根据套餐ID查询套餐信息及其关联的菜品数据。
	 *
	 * @param id 套餐的ID
	 * @return 返回一个包含套餐信息和关联菜品数据的SetmealVO对象
	 */
	@Override
	public SetmealVO getByIdWithDish(Long id) {
		Setmeal setmeal = setmealMapper.selectById(id);
		List<SetmealDish> setmealDishes = setmealDishMapper
				.selectList(new LambdaQueryWrapper<SetmealDish>().eq(SetmealDish::getSetmealId, id));

		return new SetmealVO() {{
			BeanUtils.copyProperties(setmeal, this);
			setSetmealDishes(setmealDishes);
		}};
	}
}
