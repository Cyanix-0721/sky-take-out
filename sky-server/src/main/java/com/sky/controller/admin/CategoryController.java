package com.sky.controller.admin;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 分类管理
 */
@RestController
@RequestMapping("/admin/category")
@Api(tags = "分类相关接口")
@Slf4j
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	/**
	 * 新增分类
	 * <p>
	 * 该方法用于新增分类信息。
	 *
	 * @param categoryDTO 包含分类信息的数据传输对象
	 * @return 返回操作结果
	 */
	@PostMapping
	@ApiOperation("新增分类")
	public Result<String> save(@ApiParam("分类信息") @RequestBody CategoryDTO categoryDTO) {
		log.info("新增分类：{}", categoryDTO);
		categoryService.save(categoryDTO);
		return Result.success();
	}

	/**
	 * 删除分类
	 * <p>
	 * 该方法用于根据分类ID删除分类信息。
	 *
	 * @param id 分类的唯一标识符
	 * @return 返回操作结果
	 */
	@DeleteMapping
	@ApiOperation("删除分类")
	public Result<String> deleteById(@ApiParam("分类ID") Long id) {
		log.info("删除分类：{}", id);
		categoryService.deleteById(id);
		return Result.success();
	}

	/**
	 * 启用、禁用分类
	 * <p>
	 * 该方法用于启用或禁用分类。
	 *
	 * @param status 分类的状态（启用或禁用）
	 * @param id     分类的唯一标识符
	 * @return 返回操作结果
	 */
	@PostMapping("/status/{status}")
	@ApiOperation("启用禁用分类")
	public Result<String> startOrStop(@ApiParam("状态") @PathVariable("status") Integer status, @ApiParam("分类ID") Long id) {
		categoryService.startOrStop(status, id);
		return Result.success();
	}

	/**
	 * 修改分类
	 * <p>
	 * 该方法用于修改分类信息。
	 *
	 * @param categoryDTO 包含分类信息的数据传输对象
	 * @return 返回操作结果
	 */
	@PutMapping
	@ApiOperation("修改分类")
	public Result<String> update(@ApiParam("分类信息") @RequestBody CategoryDTO categoryDTO) {
		categoryService.update(categoryDTO);
		return Result.success();
	}

	/**
	 * 分类分页查询
	 * <p>
	 * 该方法用于分页查询分类信息。
	 *
	 * @param categoryPageQueryDTO 包含分页查询参数的数据传输对象
	 * @return 返回分页查询结果
	 */
	@GetMapping("/page")
	@ApiOperation("分类分页查询")
	public Result<PageResult> page(@ApiParam("分页查询参数") CategoryPageQueryDTO categoryPageQueryDTO) {
		log.info("分页查询：{}", categoryPageQueryDTO);
		PageResult pageResult = categoryService.pageQuery(categoryPageQueryDTO);
		return Result.success(pageResult);
	}

	/**
	 * 根据类型查询分类
	 * <p>
	 * 该方法用于根据分类类型查询分类信息。
	 *
	 * @param type 分类的类型
	 * @return 返回分类信息列表
	 */
	@GetMapping("/list")
	@ApiOperation("根据类型查询分类")
	public Result<List<Category>> list(@ApiParam("分类类型") Integer type) {
		List<Category> list = categoryService.list(type);
		return Result.success(list);
	}
}