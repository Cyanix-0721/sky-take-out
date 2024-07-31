package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜品管理
 */
@RestController
@RequestMapping("/admin/dish")
@Api(tags = "菜品相关接口")
@Slf4j
public class DishController {

	@Autowired
	private DishService dishService;

	/**
	 * 新增菜品
	 * <p>
	 * 该方法用于新增菜品信息。
	 *
	 * @param dishDTO 包含菜品信息的数据传输对象
	 * @return 返回操作结果
	 */
	@PostMapping
	@ApiOperation("新增菜品")
	public Result<Void> save(@RequestBody DishDTO dishDTO) {
		log.info("新增菜品：{}", dishDTO);
		dishService.saveWithFlavor(dishDTO);
		return Result.success();
	}

	/**
	 * 菜品批量删除
	 * <p>
	 * 该方法用于批量删除菜品信息。
	 *
	 * @param ids 包含要删除的菜品ID列表
	 * @return 返回操作结果
	 */
	@DeleteMapping
	@ApiOperation("菜品批量删除")
	public Result<Void> delete(@RequestParam List<Long> ids) {
		log.info("菜品批量删除：{}", ids);
		dishService.deleteBatch(ids);
		return Result.success();
	}

	/**
	 * 修改菜品
	 * <p>
	 * 该方法用于修改菜品信息。
	 *
	 * @param dishDTO 包含菜品信息的数据传输对象
	 * @return 返回操作结果
	 */
	@PutMapping
	@ApiOperation("修改菜品")
	public Result<Void> update(@RequestBody DishDTO dishDTO) {
		log.info("修改菜品：{}", dishDTO);
		dishService.updateWithFlavor(dishDTO);
		return Result.success();
	}

	/**
	 * 菜品分页查询
	 * <p>
	 * 该方法用于分页查询菜品信息。
	 *
	 * @param dishPageQueryDTO 包含分页查询条件的数据传输对象
	 * @return 返回包含分页结果的操作结果
	 */
	@GetMapping("/page")
	@ApiOperation("菜品分页查询")
	public Result<PageResult> page(DishPageQueryDTO dishPageQueryDTO) {
		log.info("菜品分页查询:{}", dishPageQueryDTO);
		PageResult pageResult = dishService.pageQuery(dishPageQueryDTO);
		return Result.success(pageResult);
	}

	/**
	 * 根据id查询菜品
	 * <p>
	 * 该方法用于根据给定的菜品ID查询菜品信息。
	 *
	 * @param id 菜品ID
	 * @return 返回包含菜品信息的操作结果
	 */
	@GetMapping("/{id}")
	@ApiOperation("根据id查询菜品")
	public Result<DishVO> getById(@PathVariable Long id) {
		log.info("根据id查询菜品：{}", id);
		DishVO dishVO = dishService.getByIdWithFlavor(id);
		return Result.success(dishVO);
	}
}