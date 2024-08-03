package com.sky.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.sky.constant.AutoFillConstant;
import com.sky.context.BaseContext;
import org.apache.ibatis.reflection.MetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
	private static final Logger logger = LoggerFactory.getLogger(MyMetaObjectHandler.class);

	@Override
	public void insertFill(MetaObject metaObject) {
		logger.info("Start insert fill...");
		this.setFieldValByName(AutoFillConstant.CREATE_TIME, LocalDateTime.now(), metaObject);
		this.setFieldValByName(AutoFillConstant.UPDATE_TIME, LocalDateTime.now(), metaObject);
		this.setFieldValByName(AutoFillConstant.CREATE_USER, BaseContext.getCurrentId(), metaObject);
		this.setFieldValByName(AutoFillConstant.UPDATE_USER, BaseContext.getCurrentId(), metaObject);
		this.setFieldValByName(AutoFillConstant.USER_ID, BaseContext.getCurrentId(), metaObject);
	}

	@Override
	public void updateFill(MetaObject metaObject) {
		logger.info("Start update fill...");
		this.setFieldValByName(AutoFillConstant.UPDATE_TIME, LocalDateTime.now(), metaObject);
		this.setFieldValByName(AutoFillConstant.UPDATE_USER, BaseContext.getCurrentId(), metaObject);
		this.setFieldValByName(AutoFillConstant.USER_ID, BaseContext.getCurrentId(), metaObject);
	}
}
