package com.sky.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sky.context.BaseContext;
import com.sky.dao.AddressBookDAO;
import com.sky.entity.AddressBook;
import com.sky.mapper.AddressBookMapper;
import com.sky.service.AddressBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 地址簿服务实现类
 */
@Service
@Slf4j
public class AddressBookServiceImpl implements AddressBookService {
	@Autowired
	private AddressBookMapper addressBookMapper;
	@Autowired
	private AddressBookDAO addressBookDAO;

	/**
	 * 新增地址
	 *
	 * @param addressBook 地址簿对象
	 */
	@Override
	public void save(AddressBook addressBook) {
//		addressBook.setUserId(BaseContext.getCurrentId());
		// 设置默认地址为否
		addressBook.setIsDefault(0);
		addressBookMapper.insert(addressBook);
	}

	/**
	 * 根据id删除地址
	 *
	 * @param id 地址ID
	 */
	@Override
	public void deleteById(Long id) {
		addressBookMapper.deleteById(id);
	}

	/**
	 * 根据id修改地址
	 *
	 * @param addressBook 地址簿对象
	 */
	@Override
	public void update(AddressBook addressBook) {
		addressBookMapper.updateById(addressBook);
	}

	/**
	 * 设置默认地址
	 *
	 * @param addressBook 地址簿对象
	 */
	@Override
	@Transactional
	public void setDefault(AddressBook addressBook) {
		// 将当前用户的所有地址修改为非默认地址
		addressBookMapper.update(AddressBook.builder().isDefault(0).build(), new QueryWrapper<AddressBook>()
				.eq("user_id", BaseContext.getCurrentId()));

		// 将当前地址改为默认地址
		addressBook.setIsDefault(1);
		addressBookMapper.updateById(addressBook);
	}

	/**
	 * 条件查询
	 *
	 * @param addressBook 地址簿对象
	 * @return 地址簿列表
	 */
	@Override
	public List<AddressBook> list(AddressBook addressBook) {
		return addressBookDAO.list(addressBook);
	}

	/**
	 * 根据id查询
	 *
	 * @param id 地址ID
	 * @return 地址簿对象
	 */
	@Override
	public AddressBook getById(Long id) {
		return addressBookMapper.selectById(id);
	}
}