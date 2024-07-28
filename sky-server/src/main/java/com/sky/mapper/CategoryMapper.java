package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {

	/**
	 * 插入数据
	 * <p>
	 * 该方法用于插入新的分类数据。
	 *
	 * @param category 包含分类信息的实体对象
	 */
	@Insert("insert into category(type, name, sort, status, create_time, update_time, create_user, update_user)" +
			" VALUES" +
			" (#{type}, #{name}, #{sort}, #{status}, #{createTime}, #{updateTime}, #{createUser}, #{updateUser})")
	void insert(Category category);

	/**
	 * 根据id删除分类
	 * <p>
	 * 该方法用于根据分类ID删除分类信息。
	 *
	 * @param id 分类的唯一标识符
	 */
	@Delete("delete from category where id = #{id}")
	void deleteById(Long id);

	/**
	 * 根据id修改分类
	 * <p>
	 * 该方法用于根据分类ID修改分类信息。
	 *
	 * @param category 包含分类信息的实体对象
	 */
	void update(Category category);

	/**
	 * 分页查询
	 * <p>
	 * 该方法用于分页查询分类信息。
	 *
	 * @param categoryPageQueryDTO 包含分页查询参数的数据传输对象
	 * @return 返回分页查询结果
	 */
	Page<Category> pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

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