package com.sky.controller.user;

import com.sky.entity.Category;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("userCategoryController")
@RequestMapping("/user/category")
@Api(tags = "C端-分类接口")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	/**
	 * 查询分类
	 * <p>
	 * 该方法用于查询分类信息。它接收一个分类类型，并返回一个包含符合条件的分类列表。
	 *
	 * @param type 分类的类型
	 * @return 返回一个包含符合条件的分类List\<Category\>对象
	 */
	@GetMapping("/list")
	@ApiOperation("查询分类")
	public Result<List<Category>> list(Integer type) {
		List<Category> list = categoryService.list(type);
		return Result.success(list);
	}
}