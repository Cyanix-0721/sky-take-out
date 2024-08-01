package com.sky.controller.user;

import com.sky.constant.StatusConstant;
import com.sky.entity.Dish;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("userDishController")
@RequestMapping("/user/dish")
@Slf4j
@Api(tags = "C端-菜品浏览接口")
public class DishController {
	@Autowired
	private DishService dishService;

	/**
	 * 根据分类id查询菜品
	 * <p>
	 * 该方法用于根据分类ID查询菜品信息。它接收一个分类ID，并返回一个包含符合条件的菜品列表。
	 *
	 * @param categoryId 分类的ID
	 * @return 返回一个包含符合条件的菜品List\<DishVO\>对象
	 */
	@GetMapping("/list")
	@ApiOperation("根据分类id查询菜品")
	public Result<List<DishVO>> list(Long categoryId) {
		Dish dish = Dish.builder()
				.categoryId(categoryId)
				.status(StatusConstant.ENABLE) // 查询起售中的菜品
				.build();

		List<DishVO> list = dishService.listWithFlavor(dish);

		return Result.success(list);
	}
}