package com.sky.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.entity.SetmealDish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DishServiceImpl implements DishService {

	@Autowired
	private DishMapper dishMapper;
	@Autowired
	private DishFlavorMapper dishFlavorMapper;
	@Autowired
	private SetmealDishMapper setmealDishMapper;

	/**
	 * 新增菜品和对应的口味
	 * <p>
	 * 该方法用于将菜品及其对应的口味信息保存到数据库中。
	 * 它首先将菜品信息保存到菜品表中，然后获取生成的主键值，
	 * 并将该主键值设置到每个口味对象中，最后将这些口味对象保存到口味表中。
	 *
	 * @param dishDTO 包含菜品和口味信息的数据传输对象
	 */
	@Override
	@Transactional
	public void saveWithFlavor(DishDTO dishDTO) {

		Dish dish = new Dish();
		BeanUtils.copyProperties(dishDTO, dish);

		// 向菜品表插入1条数据
		dishMapper.insert(dish);

		// 获取insert语句生成的主键值
		Long dishId = dish.getId();

		List<DishFlavor> flavors = dishDTO.getFlavors();
		if (flavors != null && !flavors.isEmpty()) {
			flavors.forEach(dishFlavor -> dishFlavor.setDishId(dishId));
			// 向口味表插入n条数据
			dishFlavorMapper.insert(flavors);
		}
	}

	/**
	 * 菜品批量删除
	 *
	 * @param ids 包含要删除的菜品ID列表
	 */
	@Override
	@Transactional
	public void deleteBatch(List<Long> ids) {
		// 判断当前菜品是否能够删除---是否存在起售中的菜品
		List<Dish> dishes = dishMapper.selectBatchIds(ids);
		dishes.stream()
				.filter(dish -> StatusConstant.ENABLE.equals(dish.getStatus()))
				.findAny()
				.ifPresent(dish -> {
					// 当前菜品处于起售中，不能删除
					throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
				});

		// 判断当前菜品是否能够删除---是否被套餐关联了
		List<Long> setmealIds = setmealDishMapper.selectList(
						new LambdaQueryWrapper<SetmealDish>()
								.in(SetmealDish::getDishId, ids)
				).stream()
				.map(SetmealDish::getSetmealId) // 获取套餐ID
				.distinct()
				.collect(Collectors.toList()); //如果为空，返回空列表
		if (!setmealIds.isEmpty()) {
			// 当前菜品被套餐关联了，不能删除
			throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
		}

		// 删除菜品表中的菜品数据
		dishMapper.deleteByIds(ids);

		// 删除菜品关联的口味数据
		dishFlavorMapper.delete(new LambdaQueryWrapper<DishFlavor>().in(DishFlavor::getDishId, ids));
	}

	/**
	 * 根据id修改菜品基本信息和对应的口味信息
	 *
	 * @param dishDTO 包含菜品和口味信息的数据传输对象
	 */
	@Override
	public void updateWithFlavor(DishDTO dishDTO) {
		Dish dish = new Dish();
		BeanUtils.copyProperties(dishDTO, dish);

		// 修改菜品表基本信息
		dishMapper.updateById(dish);

		// 删除原有的口味数据
		dishFlavorMapper.delete(
				new LambdaQueryWrapper<DishFlavor>().eq(DishFlavor::getDishId, dishDTO.getId())
		);

		// 重新插入口味数据
		Optional.ofNullable(dishDTO.getFlavors())
				.filter(flavors -> !flavors.isEmpty())
				.ifPresent(flavors -> {
					flavors.forEach(dishFlavor -> dishFlavor.setDishId(dishDTO.getId()));
					dishFlavorMapper.insert(flavors);
				});
	}

	/**
	 * 菜品分页查询
	 * <p>
	 * 该方法用于分页查询菜品信息。
	 *
	 * @param dishPageQueryDTO 包含分页查询条件的数据传输对象
	 * @return 返回包含分页结果的操作结果
	 */
	@Override
	public PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO) {
		// 创建分页对象
		Page<Dish> page = new Page<>(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize());

		// 构建查询条件
		LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.like(dishPageQueryDTO.getName() != null && !dishPageQueryDTO.getName().isEmpty(), Dish::getName, dishPageQueryDTO.getName())
				.eq(dishPageQueryDTO.getCategoryId() != null, Dish::getCategoryId, dishPageQueryDTO.getCategoryId())
				.eq(dishPageQueryDTO.getStatus() != null, Dish::getStatus, dishPageQueryDTO.getStatus())
				.orderByDesc(Dish::getCreateTime);

		// 执行查询
		Page<Dish> resultPage = dishMapper.selectPage(page, queryWrapper);

		// 将结果转换为DishVO列表
		List<DishVO> dishVOList = resultPage.getRecords().stream().map(dish -> {
			DishVO dishVO = new DishVO();
			BeanUtils.copyProperties(dish, dishVO);
			return dishVO;
		}).collect(Collectors.toList());

		// 返回分页结果
		return new PageResult(resultPage.getTotal(), dishVOList);
	}

	/**
	 * 根据id查询菜品对应的口味数据
	 * <p>
	 * 该方法用于根据给定的菜品ID查询菜品及其对应的口味信息。
	 *
	 * @param id 菜品ID
	 * @return 返回包含菜品及其口味信息的操作结果
	 */
	@Override
	public DishVO getByIdWithFlavor(Long id) {
		Dish dish = dishMapper.selectById(id);
		List<DishFlavor> dishFlavors = dishFlavorMapper.selectList(
				new LambdaQueryWrapper<DishFlavor>().eq(DishFlavor::getDishId, id)
		);

		return new DishVO() {{
			BeanUtils.copyProperties(dish, this);
			setFlavors(dishFlavors);
		}};
	}
}