package com.sky.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.CategoryMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 分类业务层
 */
@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryMapper categoryMapper;
	@Autowired
	private DishMapper dishMapper;
	@Autowired
	private SetmealMapper setmealMapper;

	/**
	 * 新增分类
	 * <p>
	 * 该方法用于新增分类信息。
	 *
	 * @param categoryDTO 包含分类信息的数据传输对象
	 */
	public void save(CategoryDTO categoryDTO) {
		Category category = new Category();
		// 属性拷贝
		BeanUtils.copyProperties(categoryDTO, category);

		// 分类状态默认为禁用状态0
		category.setStatus(StatusConstant.DISABLE);

		// 设置创建时间、修改时间、创建人、修改人
		category.setCreateTime(LocalDateTime.now());
		category.setUpdateTime(LocalDateTime.now());
		category.setCreateUser(BaseContext.getCurrentId());
		category.setUpdateUser(BaseContext.getCurrentId());

		categoryMapper.insert(category);
	}

	/**
	 * 根据id删除分类
	 * <p>
	 * 该方法用于根据分类ID删除分类信息。
	 *
	 * @param id 分类的唯一标识符
	 */
	public void deleteById(Long id) {
		// 查询当前分类是否关联了菜品，如果关联了就抛出业务异常
		Long count = dishMapper.selectCount(new QueryWrapper<Dish>().eq("category_id", id));
		if (count > 0) {
			// 当前分类下有菜品，不能删除
			throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_DISH);
		}

		// 查询当前分类是否关联了套餐，如果关联了就抛出业务异常
		count = setmealMapper.selectCount(new QueryWrapper<Setmeal>().eq("category_id", id));
		if (count > 0) {
			// 当前分类下有套餐，不能删除
			throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_SETMEAL);
		}

		// 删除分类数据
		categoryMapper.deleteById(id);
	}

	/**
	 * 启用、禁用分类
	 * <p>
	 * 该方法用于启用或禁用分类。
	 *
	 * @param status 分类的状态（启用或禁用）
	 * @param id     分类的唯一标识符
	 */
	public void startOrStop(Integer status, Long id) {
		Category category = Category.builder()
				.id(id)
				.status(status)
				.updateTime(LocalDateTime.now())
				.updateUser(BaseContext.getCurrentId())
				.build();
		categoryMapper.updateById(category);
	}

	/**
	 * 修改分类
	 * <p>
	 * 该方法用于修改分类信息。
	 *
	 * @param categoryDTO 包含分类信息的数据传输对象
	 */
	public void update(CategoryDTO categoryDTO) {
		Category category = new Category();
		BeanUtils.copyProperties(categoryDTO, category);

		// 设置修改时间、修改人
		category.setUpdateTime(LocalDateTime.now());
		category.setUpdateUser(BaseContext.getCurrentId());

		categoryMapper.updateById(category);
	}

	/**
	 * 分页查询
	 * <p>
	 * 该方法用于分页查询分类信息。
	 *
	 * @param categoryPageQueryDTO 包含分页查询参数的数据传输对象
	 * @return 返回分页查询结果
	 */
	public PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO) {
		// 创建分页对象
		Page<Category> page = new Page<>(categoryPageQueryDTO.getPage(), categoryPageQueryDTO.getPageSize());

		// 执行分页查询
		Page<Category> resultPage = categoryMapper.selectPage(page, new QueryWrapper<Category>()
				.like(categoryPageQueryDTO.getName() != null && !categoryPageQueryDTO.getName().isEmpty(), "name", categoryPageQueryDTO.getName())
				.eq(categoryPageQueryDTO.getType() != null, "type", categoryPageQueryDTO.getType())
				.orderByAsc("sort")
				.orderByDesc("create_time"));

		// 获取总记录数和分类列表
		long total = resultPage.getTotal();
		List<Category> records = resultPage.getRecords();

		// 返回分页结果
		return new PageResult(total, records);
	}

	/**
	 * 根据类型查询分类
	 * <p>
	 * 该方法用于根据分类类型查询分类信息。
	 *
	 * @param type 分类的类型
	 * @return 返回分类信息列表
	 */
	public List<Category> list(Integer type) {
		QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq(type != null, "type", type);
		queryWrapper.eq("status", 1); // 确保只查询状态为启用的分类
		queryWrapper.orderByAsc("sort").orderByDesc("create_time");

		return categoryMapper.selectList(queryWrapper);
	}
}