package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 套餐管理
 */
@RestController
@RequestMapping("/admin/setmeal")
@Api(tags = "套餐相关接口")
@Slf4j
public class SetmealController {

	@Autowired
	private SetmealService setmealService;

	/**
	 * 新增套餐
	 * <p>
	 * 该方法用于新增一个套餐。它接收一个包含套餐信息的SetmealDTO对象，
	 * 并调用SetmealService的saveWithDish方法将该套餐信息保存到数据库中。
	 *
	 * @param setmealDTO 包含套餐信息的DTO对象
	 * @return 返回一个Result对象，表示操作的结果
	 */
	@PostMapping
	@ApiOperation("新增套餐")
	public Result<Void> save(@RequestBody SetmealDTO setmealDTO) {
		setmealService.saveWithDish(setmealDTO);
		return Result.success();
	}

	/**
	 * 批量删除套餐
	 * <p>
	 * 该方法用于批量删除套餐。它接收一个包含要删除的套餐ID列表，
	 * 并调用SetmealService的deleteBatch方法删除这些套餐。
	 *
	 * @param ids 包含要删除的套餐ID列表
	 * @return 返回一个Result对象，表示操作的结果
	 */
	@DeleteMapping
	@ApiOperation("批量删除套餐")
	public Result<Void> delete(@RequestParam List<Long> ids) {
		setmealService.deleteBatch(ids);
		return Result.success();
	}

	/**
	 * 修改套餐
	 * <p>
	 * 该方法用于修改一个套餐。它接收一个包含套餐信息的SetmealDTO对象，
	 * 并调用SetmealService的update方法更新该套餐信息。
	 *
	 * @param setmealDTO 包含套餐信息的DTO对象
	 * @return 返回一个Result对象，表示操作的结果
	 */
	@PutMapping
	@ApiOperation("修改套餐")
	public Result<Void> update(@RequestBody SetmealDTO setmealDTO) {
		setmealService.update(setmealDTO);
		return Result.success();
	}

	/**
	 * 套餐起售、停售
	 * <p>
	 * 该方法用于启用或禁用一个套餐。它接收一个表示套餐状态的整数和一个套餐ID，
	 * 并调用SetmealService的startOrStop方法更新该套餐的状态。
	 *
	 * @param status 表示套餐状态的整数。1表示启用，0表示禁用。
	 * @param id     套餐ID
	 * @return 返回一个Result对象，表示操作的结果
	 */
	@PostMapping("/status/{status}")
	@ApiOperation("起售停售套餐")
	public Result<Void> startOrStop(@PathVariable Integer status, @RequestParam Long id) {
		log.info("起售停售套餐：{},{}", status, id);
		setmealService.startOrStop(status, id);
		return Result.success();
	}

	/**
	 * 分页查询
	 * <p>
	 * 该方法用于分页查询套餐信息。它接收一个包含查询条件的SetmealPageQueryDTO对象，
	 * 并调用SetmealService的pageQuery方法获取分页结果。
	 *
	 * @param setmealPageQueryDTO 包含查询条件的DTO对象
	 * @return 返回一个Result对象，包含分页查询的结果
	 */
	@GetMapping("/page")
	@ApiOperation("分页查询")
	public Result<PageResult> page(SetmealPageQueryDTO setmealPageQueryDTO) {
		PageResult pageResult = setmealService.pageQuery(setmealPageQueryDTO);
		return Result.success(pageResult);
	}

	/**
	 * 根据id查询套餐，用于修改页面回显数据
	 * <p>
	 * 该方法用于根据套餐ID查询套餐信息，并返回包含套餐信息的Result对��。
	 * 它接收一个套餐ID作为参数，并调用SetmealService的getByIdWithDish方法获取套餐信息。
	 *
	 * @param id 套餐ID
	 * @return 返回一个Result对象，包含查询到的套餐信息
	 */
	@GetMapping("/{id}")
	@ApiOperation("根据id查询套餐")
	public Result<SetmealVO> getById(@PathVariable Long id) {
		SetmealVO setmealVO = setmealService.getByIdWithDish(id);
		return Result.success(setmealVO);
	}
}