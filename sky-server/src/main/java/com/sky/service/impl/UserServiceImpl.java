package com.sky.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sky.constant.MessageConstant;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.exception.LoginFailedException;
import com.sky.mapper.UserMapper;
import com.sky.properties.WeChatProperties;
import com.sky.service.UserService;
import com.sky.utils.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

	//微信服务接口地址
	public static final String WX_LOGIN = "https://api.weixin.qq.com/sns/jscode2session";

	@Autowired
	private WeChatProperties weChatProperties;
	@Autowired
	private UserMapper userMapper;

	/**
	 * 微信登录
	 * <p>
	 * 该方法处理微信用户的登录过程。
	 *
	 * @param userLoginDTO 包含用户登录信息的数据传输对象。
	 * @return User 对应于已登录用户的用户实体。
	 */
	public User wxLogin(UserLoginDTO userLoginDTO) {
		String openid = getOpenid(userLoginDTO.getCode());

		// 判断openid是否为空，如果为空表示登录失败，抛出业务异常
		if (openid == null) {
			throw new LoginFailedException(MessageConstant.LOGIN_FAILED);
		}

		// 判断当前用户是否为新用户
		User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getOpenid, openid));

		// 如果是新用户，自动完成注册
		if (user == null) {
			user = User.builder()
					.openid(openid)
					//已通过MP实现自动填充
//					.createTime(LocalDateTime.now())
					.build();
			userMapper.insert(user);
		}

		// 返回这个用户对象
		return user;
	}

	/**
	 * 调用微信接口服务，获取微信用户的openid
	 * <p>
	 * 该方法调用微信接口服务，通过传入的code获取当前微信用户的openid。
	 *
	 * @param code 微信生成的临时登录凭证code。
	 * @return String 当前微信用户的openid。
	 */
	private String getOpenid(String code) {
		// 调用微信接口服务，获得当前微信用户的openid
		Map<String, String> map = new HashMap<>();
		map.put("appid", weChatProperties.getAppid());
		map.put("secret", weChatProperties.getSecret());
		map.put("js_code", code);
		map.put("grant_type", "authorization_code");
		String json = HttpClientUtil.doGet(WX_LOGIN, map);

		JSONObject jsonObject = JSON.parseObject(json);
		return jsonObject.getString("openid");
	}
}