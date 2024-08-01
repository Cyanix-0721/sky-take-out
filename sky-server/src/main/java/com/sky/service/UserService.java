package com.sky.service;

import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;

public interface UserService {

	/**
	 * 微信登录
	 * <p>
	 * 该方法处理微信用户的登录过程。
	 *
	 * @param userLoginDTO 包含用户登录信息的数据传输对象。
	 * @return User 对应于已登录用户的用户实体。
	 */
	User wxLogin(UserLoginDTO userLoginDTO);
}