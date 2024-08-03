package com.sky.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sky.entity.AddressBook;
import com.sky.entity.Dish;
import com.sky.mapper.AddressBookMapper;
import com.sky.mapper.DishMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AddressBookDAO {

	@Autowired
	private AddressBookMapper addressBookMapper;
	
	public List<AddressBook> list(AddressBook addressBook) {
		return addressBookMapper.selectList(
				new LambdaQueryWrapper<AddressBook>()
						.like(addressBook.getUserId() != null, AddressBook::getUserId, addressBook.getUserId())
						.eq(addressBook.getPhone() != null, AddressBook::getPhone, addressBook.getPhone())
						.eq(addressBook.getIsDefault() != null, AddressBook::getIsDefault, addressBook.getIsDefault())
		);
	}
}
