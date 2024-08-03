package com.sky.service;

import com.sky.entity.AddressBook;

import java.util.List;

/**
 * AddressBookService接口定义了地址簿相关的服务方法
 */
public interface AddressBookService {


	/**
	 * 保存地址簿
	 *
	 * @param addressBook 地址簿对象
	 */
	void save(AddressBook addressBook);

	/**
	 * 根据ID删除地址簿
	 *
	 * @param id 地址簿ID
	 */
	void deleteById(Long id);


	/**
	 * 更新地址簿
	 *
	 * @param addressBook 地址簿对象
	 */
	void update(AddressBook addressBook);

	/**
	 * 设置默认地址
	 *
	 * @param addressBook 地址簿对象
	 */
	void setDefault(AddressBook addressBook);


	/**
	 * 查询地址簿列表
	 *
	 * @param addressBook 地址簿对象，包含查询条件
	 * @return 返回地址簿列表
	 */
	List<AddressBook> list(AddressBook addressBook);

	/**
	 * 根据ID查询地址簿
	 *
	 * @param id 地址簿ID
	 * @return 返回地址簿对象
	 */
	AddressBook getById(Long id);
}