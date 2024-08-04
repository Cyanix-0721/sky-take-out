package com.sky.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.sky.constant.AutoFillConstant;
import com.sky.context.BaseContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

	@Override
	public void insertFill(MetaObject metaObject) {
		log.info("Start insert fill...");
		this.setFieldValByName(AutoFillConstant.CREATE_TIME, LocalDateTime.now(), metaObject);
		this.setFieldValByName(AutoFillConstant.UPDATE_TIME, LocalDateTime.now(), metaObject);
		this.setFieldValByName(AutoFillConstant.CREATE_USER, BaseContext.getCurrentId(), metaObject);
		this.setFieldValByName(AutoFillConstant.UPDATE_USER, BaseContext.getCurrentId(), metaObject);
		this.setFieldValByName(AutoFillConstant.USER_ID, BaseContext.getCurrentId(), metaObject);
	}

	@Override
	public void updateFill(MetaObject metaObject) {
		log.info("Start update fill...");
		this.setFieldValByName(AutoFillConstant.UPDATE_TIME, LocalDateTime.now(), metaObject);
		this.setFieldValByName(AutoFillConstant.UPDATE_USER, BaseContext.getCurrentId(), metaObject);
		this.setFieldValByName(AutoFillConstant.USER_ID, BaseContext.getCurrentId(), metaObject);
	}
}
