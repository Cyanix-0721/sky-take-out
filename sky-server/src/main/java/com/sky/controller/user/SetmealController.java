package com.sky.controller.user;

import com.sky.constant.StatusConstant;
import com.sky.entity.Setmeal;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.DishItemVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("userSetmealController")
@RequestMapping("/user/setmeal")
@Api(tags = "C端-套餐浏览接口")
public class SetmealController {
	@Autowired
	private SetmealService setmealService;

	/**
	 * /**
	 * 条件查询
	 * <p>
	 * 该方法用于根据分类id查询套餐。它接收一个分类id，并返回一个包含符合条件的套餐列表。
	 *
	 * @param categoryId 分类的id
	 * @return 返回一个包含符合条件的套餐List\<Setmeal\>对象
	 */
	@GetMapping("/list")
	@ApiOperation("根据分类id查询套餐")
	public Result<List<Setmeal>> list(Long categoryId) {
		Setmeal setmeal = Setmeal.builder()
				.categoryId(categoryId)
				.status(StatusConstant.ENABLE)
				.build();

		List<Setmeal> list = setmealService.list(setmeal);
		return Result.success(list);
	}

	/**
	 * 根据套餐id查询包含的菜品列表
	 * <p>
	 * 该方法用于根据套餐id查询包含的菜品列表。它接收一个套餐id，并返回一个包含符合条件的菜品列表。
	 *
	 * @param id 套餐的id
	 * @return 返回一个包含符合条件的菜品List\<DishItemVO\>对象
	 */
	@GetMapping("/dish/{id}")
	@ApiOperation("根据套餐id查询包含的菜品列表")
	public Result<List<DishItemVO>> dishList(@PathVariable("id") Long id) {
		List<DishItemVO> list = setmealService.getDishItemById(id);
		return Result.success(list);
	}
}