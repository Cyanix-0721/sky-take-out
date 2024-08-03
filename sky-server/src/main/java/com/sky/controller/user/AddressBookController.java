package com.sky.controller.user;

import com.sky.context.BaseContext;
import com.sky.entity.AddressBook;
import com.sky.result.Result;
import com.sky.service.AddressBookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/addressBook")
@Api(tags = "C端地址簿接口")
public class AddressBookController {

	@Autowired
	private AddressBookService addressBookService;


	/**
	 * 新增地址
	 *
	 * @param addressBook 地址簿对象
	 * @return 返回操作结果
	 */
	@PostMapping
	@ApiOperation("新增地址")
	public Result<Void> save(@RequestBody AddressBook addressBook) {
		addressBookService.save(addressBook);
		return Result.success();
	}


	/**
	 * 根据id删除地址
	 *
	 * @param id 地址ID
	 * @return 返回操作结果
	 */
	@DeleteMapping
	@ApiOperation("根据id删除地址")
	public Result<Void> deleteById(Long id) {
		addressBookService.deleteById(id);
		return Result.success();
	}

	/**
	 * 根据id修改地址
	 *
	 * @param addressBook 地址簿对象
	 * @return 返回操作结果
	 */
	@PutMapping
	@ApiOperation("根据id修改地址")
	public Result<Void> update(@RequestBody AddressBook addressBook) {
		addressBookService.update(addressBook);
		return Result.success();
	}

	/**
	 * 设置默认地址
	 *
	 * @param addressBook 地址簿对象
	 * @return 返回操作结果
	 */
	@PutMapping("/default")
	@ApiOperation("设置默认地址")
	public Result<Void> setDefault(@RequestBody AddressBook addressBook) {
		addressBookService.setDefault(addressBook);
		return Result.success();
	}

	/**
	 * 查询当前登录用户的所有地址信息
	 *
	 * @return 返回当前登录用户的所有地址信息
	 */
	@GetMapping("/list")
	@ApiOperation("查询当前登录用户的所有地址信息")
	public Result<List<AddressBook>> list() {
		AddressBook addressBook = AddressBook.builder()
				.userId(BaseContext.getCurrentId())
				.build();
		List<AddressBook> list = addressBookService.list(addressBook);
		return Result.success(list);
	}

	/**
	 * 查询默认地址
	 *
	 * @return 返回默认地址簿对象
	 */
	@GetMapping("default")
	@ApiOperation("查询默认地址")
	public Result<AddressBook> getDefault() {
		// SQL: select * from address_book where user_id = ? and is_default = 1
		AddressBook addressBook = AddressBook.builder()
				.isDefault(1)
				.userId(BaseContext.getCurrentId())
				.build();
		return addressBookService.list(addressBook).stream()
				.findFirst()
				.map(Result::success)
				.orElseGet(() -> Result.error("没有查询到默认地址"));
	}


	/**
	 * 根据id查询地址
	 *
	 * @param id 地址ID
	 * @return 返回地址簿对象
	 */
	@GetMapping("/{id}")
	@ApiOperation("根据id查询地址")
	public Result<AddressBook> getById(@PathVariable Long id) {
		AddressBook addressBook = addressBookService.getById(id);
		return Result.success(addressBook);
	}
}