package com.sky.service;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;

import java.util.List;

public interface CategoryService {

	/**
	 * 新增分类
	 * <p>
	 * 该方法用于新增分类信息。
	 *
	 * @param categoryDTO 包含分类信息的数据传输对象
	 */
	void save(CategoryDTO categoryDTO);

	/**
	 * 根据id删除分类
	 * <p>
	 * 该方法用于根据分类ID删除分类信息。
	 *
	 * @param id 分类的唯一标识符
	 */
	void deleteById(Long id);

	/**
	 * 启用、禁用分类
	 * <p>
	 * 该方法用于启用或禁用分类。
	 *
	 * @param status 分类的状态（启用或禁用）
	 * @param id     分类的唯一标识符
	 */
	void startOrStop(Integer status, Long id);

	/**
	 * 修改分类
	 * <p>
	 * 该方法用于修改分类信息。
	 *
	 * @param categoryDTO 包含分类信息的数据传输对象
	 */
	void update(CategoryDTO categoryDTO);

	/**
	 * 分页查询
	 * <p>
	 * 该方法用于分页查询分类信息。
	 *
	 * @param categoryPageQueryDTO 包含分页查询参数的数据传输对象
	 * @return 返回分页查询结果
	 */
	PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

	/**
	 * 根据类型查询分类
	 * <p>
	 * 该方法用于根据分类类型查询分类信息。
	 *
	 * @param type 分类的类型
	 * @return 返回分类信息列表
	 */
	List<Category> list(Integer type);
}