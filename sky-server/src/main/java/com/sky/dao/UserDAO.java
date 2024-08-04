package com.sky.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sky.dto.OrderPageQueryDTO;
import com.sky.entity.Order;
import com.sky.entity.User;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Repository
public class UserDAO {
	@Autowired
	private UserMapper userMapper;

	public Integer countByMap(Map<String, Object> map) {
		LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();

		queryWrapper
				.ge(map.get("begin") != null, User::getCreateTime, map.get("begin"))
				.le(map.get("end") != null, User::getCreateTime, map.get("end"));

		return Math.toIntExact(userMapper.selectCount(queryWrapper));
	}
}
